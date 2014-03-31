package com.it.epolice.sync.solr;

import com.google.common.base.Strings;
import com.google.gson.*;
import com.it.epolice.domain.Image;
import com.reagroup.china.listing.*;
import com.reagroup.china.listing.features.BuildingFeature;
import com.reagroup.china.listing.salesrep.SalesRep;
import com.reagroup.china.location.LocationService;
import com.reagroup.china.publisher.normalizer.CaseNormalizer;
import com.reagroup.china.publisher.normalizer.Normalizer;
import com.reagroup.china.util.AuPhoneNumberParser;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;

public class SolrDocumentBuilder {

    private Gson gson;

    public SolrDocumentBuilder() {
        this.gson = buildGson();
    }

    public SolrInputDocument build(Image image) {
        SolrInputDocument doc = new SolrInputDocument();

        buildBaseFields(doc, image);
        buildVehicle(doc, image);
        buildGeo(doc, image);

        return doc;
    }

    private void buildProjectInfo(SolrInputDocument doc, Listing listing) {
        if (listing.getProjectBranding() != null) {
            doc.addField("project_branding_primary_color", listing.getProjectBranding().getPrimaryColor());
            doc.addField("project_branding_text_color", listing.getProjectBranding().getTextColor());
        }
    }

    private String buildTextField(Listing listing) {
        StringBuilder builder = new StringBuilder();

        Map<String, Regional> regionals = listing.getRegionals();
        for (String lang : regionals.keySet()) {
            Regional regional = regionals.get(lang);
            builder.append(Strings.nullToEmpty(regional.getTagline())).append(' ');
            builder.append(Strings.nullToEmpty(regional.getDescription())).append(' ');
        }

        return builder.toString();
    }

    private void buildBaseFields(Listing listing, SolrInputDocument doc) {
        doc.addField("documentid", listing.getListingId());

        doc.addField("listingtype", normalizer.normalize(listing.getChannels(), CaseNormalizer.CaseType.LOWER));
        doc.addField("listdate", listing.getImportedCreatedAt());
        doc.addField("updated_at", listing.getUpdatedAt());
        doc.addField("status", listing.getImportedStatus());

        doc.addField("propertytype", normalizer.normalize(listing.getPropertyType(), CaseNormalizer.CaseType.LOWER));
        doc.addField("search_property_type", resolveListingSearchPropertyType(listing.getPropertyType()));
        doc.addField("tagline", listing.getRegional(Regions.EN).getTagline());
        doc.addField("description", listing.getRegional(Regions.EN).getDescription());

        doc.addField("numbeds", listing.getNumBeds());
        doc.addField("parkingspaces", listing.getNumParkingSpaces());
        doc.addField("numfullbaths", listing.getNumBaths());

        doc.addField("audience", "reasites");
        doc.addField("country", "au");

        doc.addField("land_size", listing.getLandSize());
        doc.addField("land_size_units", listing.getLandSizeUnits());
        if (listing.getLandLot() != null) {
            doc.addField("frontage", listing.getLandLot().getFrontage());
            doc.addField("rear", listing.getLandLot().getRear());
            doc.addField("cross_over", listing.getLandLot().getCrossOver());
            doc.addField("left_depth", listing.getLandLot().getLeftDepth());
            doc.addField("right_depth", listing.getLandLot().getRightDepth());
        }
        doc.addField("listingversion", listing.getVersion());

        String projectProfileId = resolveListingProjectId(listing);
        if (!projectProfileId.isEmpty()) {
            doc.addField("project_id", projectProfileId);
        }
    }

    private String resolveListingProjectId(Listing listing) {
        if (null != listing.getProject()) {
            return listing.getProject().getListingId();
        }

        if (listing.isProjectProfile()) {
            return listing.getListingId();
        }

        return "";
    }

    private List<String> resolveListingSearchPropertyType(String propertyType) {
        if (searchPropertyTypeMap.get(propertyType) == null) {
            return newArrayList(normalizer.normalize(propertyType, CaseNormalizer.CaseType.LOWER));
        }
        return searchPropertyTypeMap.get(propertyType);
    }

    private void buildBuildingFeatures(SolrInputDocument doc, Listing listing) {
        BuildingFeature buildingFeature = listing.getBuildingFeature();
        if (buildingFeature != null) {
            doc.addField("building_features", gson.toJson(listing.getBuildingFeature()));
            doc.addField("new_construction", listing.getBuildingFeature().isNewConstruction());
        }
    }

    private Map<String, List<Agent>> getContacts(Listing listing) {
        Map<String, List<Agent>> contacts = newHashMap();
        List<Agent> cnAgents = (listing.getAvailableBrokers().size() == 0) ? getAgentsFromLiaison(listing) : getAgentsFromBrokers(listing);
        contacts.put("CN", cnAgents);
        contacts.put("AU", asList(listing.getOriginalAgent()));
        return contacts;
    }

    private List<Agent> getAgentsFromLiaison(Listing listing) {
        Liaison liaison = liaisonService.get();
        Agency agency = listing.getAgency();
        if (agency != null) {
            liaison.setAgencyId(agency.getAgencyId());
        }
        liaison.setAgentType(Agent.AgentType.LIAISON);
        return asList((Agent) liaison);
    }

    private List<Agent> getAgentsFromBrokers(Listing listing) {
        List<Agent> agents = newArrayList();
        for (Broker availableBroker : listing.getAvailableBrokers()) {
            agents.add(buildAgentFromBroker(availableBroker));
        }
        return agents;
    }

    private Agent buildAgentFromBroker(Broker broker) {
        SalesRep sales = broker.getSalesRep();
        Agent agent = new Agent();
        agent.setRegion(broker.getRegion());
        agent.setId(sales.getSalesRepId());
        agent.setName(sales.getName());
        agent.setImages(asList(new Image(sales.getSalesRepImage(), "", "")));
        agent.setAgencyLogos(asList(new Image(sales.getSalesRepCompanyLogo(), "", "")));
        agent.setAgencyName(sales.getCompanyName());
        agent.setAddress(new Address(sales.getOfficeAddress()));
        agent.setAgentType(Agent.AgentType.SALES_REPRESENTATIVE);

        agent.setContact(new Contact(
                sales.getMobile(),
                sales.getOfficePhone(),
                sales.getEmail(),
                sales.getOfficeEmail())
        );
        return agent;
    }

    private void buildPrice(Listing listing, SolrInputDocument doc) {
        if (null != listing.getPrice()) {
            doc.addField("listprice", listing.getPrice().getAmount());
            doc.addField("display_price", listing.getRegional(Regions.EN).getPrice());
            if(listing.isProjectProfile()) {
                doc.addField("price_hidden", listing.getPriceRange() == null);
            }
            else {
                doc.addField("price_hidden", listing.getPrice().isHidden());
            }
        }
    }

    private void buildGeo(Listing listing, SolrInputDocument doc) {
        List<Map> externalUrls = newArrayList();
        for (ExternalUrl externalUrl : listing.getExternalUrls()) {
            Map url = newHashMap();
            url.put("url", externalUrl.getUrl());
            url.put("type", externalUrl.getType());
            externalUrls.add(url);
        }
        doc.addField("externalurls", new Gson().toJson(externalUrls));
        doc.addField("video_link", StringUtils.trimToEmpty(listing.getVideoLink()));
    }

    private void buildAddress(SolrInputDocument doc, Listing listing) {
        doc.addField("streetaddr", listing.getAddress().getStreet());
        doc.addField("streetaddr_hidden", listing.getAddress().streetAddressHidden());
        doc.addField("city", listing.getAddress().getLocality().getName());
        doc.addField("state", listing.getAddress().getSubdivision().getName());
        doc.addField("postalcode", listing.getAddress().getPostcode());
        doc.addField("flat", listing.getAddress().getCoordinate().getLatitude());
        doc.addField("flon", listing.getAddress().getCoordinate().getLongitude());

        List<String> srchLocationIds = newArrayList();
        Address address = listing.getAddress();
        if (StringUtils.isNotEmpty(address.getLocality().getId())) {
            srchLocationIds.add(address.getLocality().getId());
        }
        if (!Strings.isNullOrEmpty(address.getPostcode())) {
            srchLocationIds.add(String.format("PC-%s", address.getPostcode()));
        }

        srchLocationIds.add(address.getSubdivision().getId());
        srchLocationIds.addAll(locationService.getIncludingRegion(address.getLocality().getId()));
        doc.addField("srchlocationids", srchLocationIds);

        if (StringUtils.isNotEmpty(address.getLocality().getId())) {
            doc.addField("surrlocationids",
                    locationService.getSurroundingSuburbs(address.getLocality().getId()));
        }
    }

    private void buildVehicle(SolrInputDocument doc, Listing listing) {
        doc.addField("listerid", listing.getAgent().getId());
        doc.addField("listername", listing.getAgent().getName());
        doc.addField("listerphone", AuPhoneNumberParser.parse(listing.getAgent().getContact().getPhone()));
        doc.addField("listeremail", listing.getAgent().getContact().getEmails());
        doc.addField("listerphotos", serializeImages(listing.getAgent().getImages()));

        doc.addField("listagentid", listing.getAgency().getAgencyId());
        doc.addField("listagentname", listing.getAgency().getName());
        doc.addField("listagenturl", listing.getAgency().getWebsite());
        doc.addField("listagentprimarycolor", listing.getAgency().getBranding().getPrimaryColor());
        doc.addField("listagentsecondarycolor", listing.getAgency().getBranding().getSecondaryColor());
        doc.addField("listagenttextcolor", listing.getAgency().getBranding().getTextColor());

        doc.addField("listagentlogos", serializeImages(listing.getAgency().getBranding().getImages()));
        doc.addField("listagentstate", listing.getAgency().getAddress().getSubdivision().getName());
        doc.addField("listagentcity", listing.getAgency().getAddress().getLocality().getName());
        doc.addField("listagentstreet", listing.getAgency().getAddress().getStreet());
        doc.addField("listagentpostal", listing.getAgency().getAddress().getPostcode());

        doc.addField("listagentemail", listing.getAgency().getContact().getEmails());
        doc.addField("listagentfax", listing.getAgency().getContact().getFax());
        doc.addField("listagentofficephone", AuPhoneNumberParser.parse(listing.getAgency().getContact().getPhone()));
    }

    private List<String> serializeImages(List<Image> imageList) {
        List<String> listerAgentLogos = new ArrayList<String>();
        for (Image image : imageList) {
            listerAgentLogos.add(gson.toJson(image));
        }
        return listerAgentLogos;
    }

    private Gson buildGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Address.class, new JsonSerializer<Address>() {
            @Override
            public JsonElement serialize(Address src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject json = new JsonObject();
                json.addProperty("postalcode", src.getPostcode());
                json.addProperty("streetaddr", src.getStreet());
                json.addProperty("country", src.getCountry());
                json.addProperty("city", src.getLocality().getName());
                json.addProperty("state", src.getSubdivision().getName());
                return json;
            }
        });
        return builder.create();
    }
}

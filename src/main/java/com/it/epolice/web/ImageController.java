package com.it.epolice.web;
 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.it.epolice.domain.Image;
import com.it.epolice.sync.ImageService;
import com.it.epolice.sync.parser.ImageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

	@RequestMapping(value="/syncRaw", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public @ResponseBody String syncString(@RequestBody String request){

        List<Image> images = new ImageParser().parse(request);

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.toJson(imageService.sync(images));
	}

    //curl -XPOST http://localhost:8081/service/sync -H "Content-Type:application/json" -d '{ "name":"wang"}'
    @ResponseBody
    @RequestMapping(value="sync", method = RequestMethod.POST, consumes = "application/json")
    public String syncObject(@RequestBody Image image) throws Exception {

        System.out.println("receive the request from post" + image);
        return "hello";
    }



}
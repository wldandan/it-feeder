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
import sun.net.www.http.HttpClient;

import java.util.List;
import java.util.concurrent.Executor;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    private Executor executor;

	@RequestMapping(value="/syncRaw",
            method = RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public @ResponseBody String syncString(@RequestBody String request){

        List<Image> images = new ImageParser().parse(request);

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.toJson(imageService.sync(images));
	}

    @RequestMapping(value="/list",
            method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String list(@RequestBody String request){

        Gson gson = new GsonBuilder().create();
        return gson.toJson(imageService.getImages());
    }

    //curl -XPOST http://localhost:8081/service/handle -H "Content-Type:application/json" -d '{ "name":"wang"}'
    @ResponseBody
    @RequestMapping(value="sync", method = RequestMethod.POST, consumes = "application/json")
    public String syncObject(@RequestBody Image image) throws Exception {

        System.out.println("receive the request from post" + image);
        return "hello";
    }


    class ImageSyncTask implements Runnable{

        private final ImageService imageService;
        private HttpClient client;
        private final List<Image> images;

        ImageSyncTask(ImageService imageService, List<Image> images, HttpClient client) {
            this.imageService = imageService;
            this.images = images;
        }

        @Override
        public void run() {
            imageService.sync(images);
        }
    }

}
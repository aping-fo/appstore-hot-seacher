package com.foo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.List;

import static com.alibaba.fastjson.JSON.parse;

/**
 * Created by shiping.fu on 2017/5/13 0013.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        SslContextFactory sslContextFactory = new SslContextFactory();
        HttpClient httpClient = new HttpClient(sslContextFactory);
        httpClient.setFollowRedirects(false);
        httpClient.start();

        Request request = httpClient.newRequest("https://itunes.apple.com/WebObjects/MZStore.woa/wa/search?clientApplication=Software&term="+ URLEncoder.encode("王者荣耀","utf8"));
        request.method(HttpMethod.GET);
        request.agent("AppStore/2.0 iOS/8.4 model/iPhone7,2 build/12H143 (6; dt:106)");
        request.header("X-Disd", "1221203839");
        request.header("X-Apple-Store-Front", "143465-19,29 ab:aF574PC1 t:native");
        List<HttpCookie> list = HttpCookie.parse("wosid-lite=xUw7nQwXbm4JVpcZvlNCQ0; ndcd=wc1.1.w-855182.1.2.wMPvRa5D_shpNlCj5roo-Q%2C%2C.wmKzW2WpKrsGfWjcN-61XwK52D5P5XL_sP7IpBjNzZ5m6csSSMNnZhqYxRBLTIUdbYtsOfFkm3VbudpLTmrncTl1cCTD1k-yJiacAaZk5xN09bIe6opEil9GAY_CoLVjtsUeJWMOBicAuFr9pxzhxAoNy4vxLXNm6dlkuum8oo8%2C; mz_at_ssl-1221203839=AwUAAAFqAAE6EAAAAABXMbtdzy8W2BczmuRY4Ev+2/DnIcFFSLc=; amia-1221203839=1wccA3O9dMEzewvhC3fiNn6fXCWlBl/jWXLGOonymemCuxLxk6Bbko3gR+r94lcckaZ0uVYfCgUbSBoC1D45zw==; mzf_in=502449; mt-tkn-1221203839=AooaLG13fYHEI9gy860bbQIIDt9PXkuWqSooPEC6XX6vzjFy4MwLdsLC6RADiOYFMnyB2F+jyEbrLEzrimhuY4k8KGw2T+YB2mEB2QwHffI7jt7pRAe7pqBPn+s60Gl3gSItG9Fg6opALnJxVpe4v6fPkfP11Dws/ujGNpkt6Niwv3DMVOLFSmpGL11CIuQfHrfEx4M=; itspod=50; xp_ci=3z2TQeWAzGnuz4RizCaZzvQAZxX3q; xt-b-ts-1221203839=1462781189567; mz_at0-1221203839=AwQAAAFqAAE6EAAAAABXMEUE/jzKFYF8vXJWYIhKwhZagFStPJA=; mt-asn-1221203839=5; hsaccnt=1; X-Dsid=1221203839; amp=TElg/7Ozr4dRajX3UN/fm0tTsZ7Rg7cEC9rWgvxN7+ZFN/7JZV6s83FXSn4SwCEFvrCB38RJOfNgFbWDDnxwj4iIgYNyXlIC8voWBTCXkStyUQ6kknRk58ybvrdm1EwDrxuWMlJLJti5Glh2UNbd2/gMjqAJe1He9DK6997D4Gs=; xt-src=b");
        request.cookie(list.get(0));
        ContentResponse response = request.send();


        JSONObject jsonObject = (JSONObject) JSON.parse(new String(response.getContent()));
        JSONObject jsonObject1 = (JSONObject) jsonObject.get("storePlatformData");
        JSONObject jsonObject2 = (JSONObject) jsonObject1.get("native-search-lockup-search");
        JSONObject jsonObject3 = (JSONObject) jsonObject2.get("results");

        jsonObject3.forEach((o1,o2) ->{
            JSONObject o = (JSONObject)o2;
            String name = o.getString("name");
            JSONObject o5 = (JSONObject)o.get("contentRatingsBySystem");
            JSONObject o6 = (JSONObject)o5.get("appsApple");
            int rank = o6.getIntValue("rank");

            System.out.println(name + " -->" + rank);

        });

        System.out.println(jsonObject3);
    }
}

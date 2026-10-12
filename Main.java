package dsa;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.*;import java.net.*;import java.nio.charset.StandardCharsets;
public class Main {
  public static void main(String[] args) throws Exception {
    int port=args.length>0?Integer.parseInt(args[0]):8080;
    HttpServer s=HttpServer.create(new InetSocketAddress(port),0);
    s.createContext("/", e -> serve(e,"/static/index.html","text/html; charset=UTF-8"));
    s.start();
    System.out.println("3D Graphic Designing website running at http://localhost:"+port);
  }
  static void serve(HttpExchange e,String resource,String type)throws IOException{
    try(InputStream in=Main.class.getResourceAsStream(resource)){
      if(in==null){send(e,404,"Not found","text/plain; charset=UTF-8");return;}
      byte[] b=in.readAllBytes();e.getResponseHeaders().set("Content-Type",type);e.sendResponseHeaders(200,b.length);try(OutputStream o=e.getResponseBody()){o.write(b);}
    }
  }
  static void send(HttpExchange e,int code,String body,String type)throws IOException{
    byte[] b=body.getBytes(StandardCharsets.UTF_8);e.getResponseHeaders().set("Content-Type",type);e.sendResponseHeaders(code,b.length);try(OutputStream o=e.getResponseBody()){o.write(b);}
  }
}
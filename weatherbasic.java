import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class weatherbasic {
    public static void main(String[] args) {
        try{
            Scanner s=new Scanner(System.in);
            String city;
            String state;
            String country;
            System.out.println("Enter City Name: ");
            city=s.nextLine();
            System.out.println("Enter State Name: ");
            state=s.nextLine();
            System.out.println("Enter Country Name: ");
            country=s.nextLine();
            URL url=new URL("http://api.openweathermap.org/geo/1.0/direct?q="+city+","+state+","+country+"&limit=1&appid=7cfda1d658ec98945198009d7008544c");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int rc=con.getResponseCode();
            System.out.println(rc);
            if(rc!=200){
                System.out.println("error in connection");
            }
            else{
                String data="";
                Scanner sc=new Scanner(url.openStream());
                while (sc.hasNext()){
                    data+=sc.nextLine();
                }
                sc.close();
//                System.out.println(data);
                JSONParser par=new JSONParser();
                JSONArray arr=(JSONArray)par.parse(data);
//                System.out.println(arr);
//                System.out.println(arr.get(0));
                JSONObject obj=null;
                for (int i = 0; i <arr.size() ; i++) {
                    obj=(JSONObject)arr.get(i);
//                    System.out.println(obj.get("lat"));
//                    System.out.println(obj.get("lon"));
                    double lat=(double) obj.get("lat");
                    double lon=(double) obj.get("lon");
//                    System.out.println( lat+" "+lon);
                    URL url2=new URL("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=7cfda1d658ec98945198009d7008544c");
                    HttpURLConnection con2=(HttpURLConnection) url2.openConnection();
                    con2.setRequestMethod("GET");
                    con2.connect();
                    int rc2=con.getResponseCode();
                    System.out.println(rc2);
                    if(rc2!=200){
                        System.out.println("error in connection");
                    }
                    else {
                        String data2 ="";
                        Scanner sc2 = new Scanner(url2.openStream());
                        while (sc2.hasNext()) {
                            data2 += sc2.nextLine();
                        }
                        sc2.close();
//                            System.out.println(data2);
                        JSONParser par2=new JSONParser();
                        JSONObject obj2=(JSONObject) par2.parse(data2);
//                        System.out.println(obj2);
                        JSONObject obj3=(JSONObject) obj2.get("main");
//                        System.out.println(obj3);
                        double temp=(double) obj3.get("temp");
                        temp=temp-273.15;
//                        System.out.println(temp);
                        JSONObject obj4=(JSONObject) obj2.get("wind");
//                        System.out.println(obj4);
                        double spee=(double)obj4.get("speed");

                        System.out.println("Weather Report of your city is: ");
                        System.out.println("Temperature: "+temp+" °C");
                        System.out.println("Humidity: "+obj3.get("humidity"));
                        System.out.println("Pressure: "+obj3.get("pressure"));
                        System.out.println("Wind_Speed: "+spee);
                    }
                }
            }
        }
        catch(Exception e){

        }
    }
}

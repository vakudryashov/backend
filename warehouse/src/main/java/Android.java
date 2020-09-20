import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Android {

    public static void main(String[] args) {
        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.post("http://46.17.104.250:8189/api/v1/auth")
                    .header("Content-Type", "application/json")
                    .body("{\r\n    \"username\": \"admin\",\r\n    \"password\": \"admin\"\r\n\r\n}")
                    .asString();
            System.out.println(response);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}

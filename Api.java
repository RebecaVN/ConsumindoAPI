import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsultaCEP {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Digite o CEP desejado (apenas números): ");
            String cep = reader.readLine().trim(); // Lê o CEP digitado pelo usuário
            
            if (validarCEP(cep)) {
                consultarCEP(cep);
            } else {
                System.out.println("CEP inválido. Certifique-se de digitar apenas números.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean validarCEP(String cep) {
        // Verifica se o CEP contém apenas números e possui 8 dígitos
        return cep.matches("\\d{8}");
    }

    private static void consultarCEP(String cep) {
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                System.out.println(response.toString());
            } else {
                System.out.println("Erro na consulta. Código de resposta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

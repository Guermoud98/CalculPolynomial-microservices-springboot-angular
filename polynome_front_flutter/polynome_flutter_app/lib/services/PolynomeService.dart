import 'dart:convert';
import 'package:http/http.dart' as http;


class PolynomeService {
  final String baseUrl = 'http://10.0.2.2:8082'; // API Gateway URL

  Future<List<dynamic>> getAllPolynomes() async {
    final response = await http.get(Uri.parse('$baseUrl/polynomes/all'));
    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Failed to load polynomes');
    }
  }

  Future<void> savePolynome(Map<String, dynamic> data) async {
    final response = await http.post(
      Uri.parse('$baseUrl/polynomes/save'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(data),
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to save polynome');
    }
  }
}

//new
class RacineService {
  final String baseUrl = 'http://10.0.2.2:8083';


  Future<String> calculateRoots(String expression) async {
  final formattedExpression = expression.replaceAll(RegExp(r'\s+'), ''); 
  final response = await http.post(
    Uri.parse('$baseUrl/racines/calculer'),
    headers: {'Content-Type': 'application/json'},
    body: json.encode({'expression': formattedExpression}),
  );

  if (response.statusCode == 200) {
    return response.body; 
  } else {
    throw Exception('Failed to calculate roots');
  }
}


}




class FactorisationService {
  final String baseUrl = 'http://10.0.2.2:8084'; // Factorisation service URL

  Future<String> factorize(String polynome) async {
    // Encode the polynomial to ensure it is URL safe
    final formattedPolynome = polynome.replaceAll('^', '%5E'); // Replace '^' with '%5E'
    final encodedBody = 'polynome=$formattedPolynome'; // Format the body as a query string

    final response = await http.post(
      Uri.parse('$baseUrl/api/factorisation/factorize'),
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      body: encodedBody, // Pass the encoded body
    );

    if (response.statusCode == 200) {
    
      return response.body; 
    } else {
      // Log or throw an error for debugging
      print('Error: ${response.statusCode} - ${response.body}');
      throw Exception('Failed to factorize the polynomial');
    }
  }
}

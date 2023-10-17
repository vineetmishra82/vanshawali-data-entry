import 'package:flutter/cupertino.dart';

class ApplicationData extends StatelessWidget {
  static var user;
  static List<String> buttons = [];
  static String currentText = "";
  static String baseUrl =
      "https://voice-analysis-backend-c85620e5ed5b.herokuapp.com/";

  const ApplicationData({super.key});

  static String registrationUrl(String mobile, String name) {
    return "${baseUrl}newUser?mobile=$mobile&name=$name";
  }

  static String loginUrl(String mobile) {
    return baseUrl + "users/" + mobile;
  }

  static String getAllWords() {
    return "${baseUrl}allWords";
  }

  @override
  Widget build(BuildContext context) {
    return Container();
  }
}

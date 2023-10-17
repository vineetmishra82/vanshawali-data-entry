import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:modal_progress_hud_nsn/modal_progress_hud_nsn.dart';
import 'package:voice_recognition_mo/ApplicationData.dart';

import 'HomeScreen.dart';
import 'RegisterUser.dart';

class LoginScreen extends StatefulWidget {
  static String id = "LoginScreen";

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  String mobileNum = "";
  var showSpinner = false;
  TextEditingController mobileNumController = TextEditingController();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'Login to your account',
          style: TextStyle(fontWeight: FontWeight.bold, color: Colors.white),
        ),
        backgroundColor: Colors.indigo,
      ),
      body: ModalProgressHUD(
        inAsyncCall: showSpinner,
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              TextField(
                controller: mobileNumController,
                decoration: InputDecoration(
                  labelText: 'Mobile Number',
                  labelStyle:
                      TextStyle(fontSize: 14, fontWeight: FontWeight.bold),
                  hintText: 'Enter your mobile number',
                ),
                keyboardType: TextInputType.phone,
                onChanged: (value) {},
              ),
              SizedBox(height: 20),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: () async {
                      setState(() {
                        showSpinner = true;
                      });
                      var url = Uri.parse(
                          ApplicationData.loginUrl(mobileNumController.text));
                      print(url);
                      var response = await http.get(url);
                      if (response.body == "") {
                        final snackBar = SnackBar(
                            content: const Text(
                                "You are not registered, Register yourelf first"),
                            action: SnackBarAction(
                                label: 'Error', onPressed: () {}));
                        ScaffoldMessenger.of(context).showSnackBar(snackBar);
                      } else {
                        print(response.body);
                        ApplicationData.user = json.decode(response.body);
                        await getButtonsSet();
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => HomeScreen()));
                      }

                      setState(() {
                        showSpinner = false;
                      });
                    },
                    child: Text('Login'),
                  ),
                  SizedBox(
                    width: 20,
                  ),
                  InkWell(
                    child: Text(
                      "Register",
                      style: TextStyle(
                          color: Colors.purple,
                          fontStyle: FontStyle.italic,
                          fontWeight: FontWeight.bold),
                    ),
                    onTap: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => RegisterUser()));
                    },
                  )
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  getButtonsSet() async {
    var url = Uri.parse(ApplicationData.getAllWords());
    var response = await http.get(url);
    List<dynamic> responses = json.decode(response.body);

    for (var response in responses) {
      print(response['word']);
      ApplicationData.buttons.add(response['word']);
    }
  }
}

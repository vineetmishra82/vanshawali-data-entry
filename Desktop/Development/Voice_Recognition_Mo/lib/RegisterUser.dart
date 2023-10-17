import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:modal_progress_hud_nsn/modal_progress_hud_nsn.dart';
import 'package:voice_recognition_mo/ApplicationData.dart';
import 'package:voice_recognition_mo/LoginScreen.dart';

class RegisterUser extends StatefulWidget {
  static String id = "RegisterUser";

  @override
  State<RegisterUser> createState() => _RegisterUserState();
}

class _RegisterUserState extends State<RegisterUser> {
  var remarks = "User doesnt exist";
  String mobileNum = "";
  TextEditingController nameController = TextEditingController();
  TextEditingController mobileNumController = TextEditingController();
  String name = "";
  bool showSpinner = false;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.indigo,
        title: Text(
          'Register Yourself',
          style: TextStyle(fontWeight: FontWeight.bold, color: Colors.white),
        ),
      ),
      body: ModalProgressHUD(
        inAsyncCall: showSpinner,
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              TextField(
                controller: mobileNumController,
                decoration: InputDecoration(
                  labelText: 'Your Mobile Number',
                  labelStyle:
                      TextStyle(fontSize: 14, fontWeight: FontWeight.bold),
                  hintText: 'Enter your mobile number',
                ),
                keyboardType: TextInputType.phone,
                onChanged: (value) {
                  setState(() {
                    mobileNum = value;
                  });
                },
              ),
              SizedBox(
                height: 50,
              ),
              TextField(
                controller: nameController,
                decoration: const InputDecoration(
                    labelText: 'Your Name',
                    labelStyle:
                        TextStyle(fontSize: 14, fontWeight: FontWeight.bold),
                    hintText: 'Enter your name'),
                keyboardType: TextInputType.text,
                onChanged: (value) {
                  setState(() {
                    name = value;
                  });
                },
              ),
              SizedBox(
                height: 20,
              ),
              ElevatedButton(
                onPressed: () async {
                  showSpinner = true;
                  if (name.isNotEmpty && mobileNum.isNotEmpty) {
                    var url = Uri.parse(
                        ApplicationData.registrationUrl(mobileNum, name));
                    print(url);

                    nameController.clear();
                    mobileNumController.clear();
                    var response = await http.post(url);
                    print("Response to login is " + response.body);
                    if (response.body == "1") {
                      setState(() {
                        showSpinner = false;
                      });
                      final snackBar = SnackBar(
                          content: const Text(
                              "User created successfully !..You can login now"),
                          action: SnackBarAction(
                              label: 'Success',
                              onPressed: () {
                                print("snackkbarrr pressed");
                                Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) => LoginScreen()));
                              }));
                      ScaffoldMessenger.of(context).showSnackBar(snackBar);
                    } else if (response.body == "-1") {
                      setState(() {
                        showSpinner = false;
                      });
                      final snackBar = SnackBar(
                        content: const Text(
                            "Mobile already registered, you can login !"),
                        action: SnackBarAction(
                            label: 'Duplicate Mobile',
                            onPressed: () {
                              MaterialPageRoute(
                                  builder: (context) => LoginScreen());
                            }),
                      );
                      ScaffoldMessenger.of(context).showSnackBar(snackBar);
                    } else {
                      setState(() {
                        showSpinner = false;
                      });
                      final snackBar = SnackBar(
                        content: const Text("Server error..try again !"),
                        action: SnackBarAction(
                            label: 'Error',
                            onPressed: () {
                              MaterialPageRoute(
                                  builder: (context) => LoginScreen());
                            }),
                      );
                      ScaffoldMessenger.of(context).showSnackBar(snackBar);
                    }
                  } else {
                    final snackBar = SnackBar(
                      content: const Text("All Fields are must !"),
                      action: SnackBarAction(
                        label: 'Empty Fields',
                        onPressed: () {},
                      ),
                    );
                    ScaffoldMessenger.of(context).showSnackBar(snackBar);
                    setState(() {
                      showSpinner = false;
                    });
                  }
                },
                child: Text('Register'),
              ),
              SizedBox(
                height: 20,
              ),
              InkWell(
                child: Text(
                  "Back to Login",
                  style: TextStyle(
                      color: Colors.green, fontWeight: FontWeight.bold),
                ),
                onTap: () {
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => LoginScreen()));
                },
              )
            ],
          ),
        ),
      ),
    );
  }
}

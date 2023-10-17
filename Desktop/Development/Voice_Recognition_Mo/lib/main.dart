import 'package:flutter/material.dart';
import 'package:voice_recognition_mo/HomeScreen.dart';

import 'LoginScreen.dart';
import 'RegisterUser.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue.shade100),
          useMaterial3: true,
        ),
        home: LoginScreen(),
        routes: {
          LoginScreen.id: (context) => LoginScreen(),
          HomeScreen.id: (context) => HomeScreen(),
          RegisterUser.id: (context) => RegisterUser(),
        });

    //routes:
    // /LoginScreen.id : (context) => LoginScreen(),
    // HomePage.id: (context) => HomePage(ApplicationData.mobile),
    // RegisterUser.id: (context) => RegisterUser(),
    //  );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(),
      // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

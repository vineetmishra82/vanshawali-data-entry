import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_speech/config/recognition_config.dart';
import 'package:google_speech/config/recognition_config_v1.dart';
import 'package:google_speech/speech_client_authenticator.dart';
import 'package:google_speech/speech_to_text.dart';
import 'package:modal_progress_hud_nsn/modal_progress_hud_nsn.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:voice_recognition_mo/ApplicationData.dart';

import 'Recorder.dart';

class HomeScreen extends StatefulWidget {
  static String id = "HomeScreen";

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  String mobileNum = ApplicationData.user['mobileNum'];
  String userName = ApplicationData.user['userName'];
  int score = ApplicationData.user['score'];
  bool showSpinner = false;
  bool showRecording = false;
  String audioPath = '';
  bool recognizing = false;
  bool recognizeFinished = false;
  String text = '';
  bool result = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: null,
        actions: [],
        title: Text(
          'Welcome, ' + userName,
          style: TextStyle(fontWeight: FontWeight.bold, color: Colors.black),
        ),
        backgroundColor: Colors.lightGreenAccent,
      ),
      body: showRecording
          ? getRecordingApparatus()
          : ModalProgressHUD(
              inAsyncCall: showSpinner,
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Text(
                          "Your Score : $score",
                          style: TextStyle(
                              fontSize: 15, fontWeight: FontWeight.bold),
                        ),
                      ],
                    ),
                    SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.only(left: 10),
                      child: Container(
                        height: 500,
                        child: ListView.builder(
                          itemCount: ApplicationData.buttons.length,
                          scrollDirection: Axis.vertical,
                          itemBuilder: (context, index) {
                            return Padding(
                              padding: const EdgeInsets.only(top: 20),
                              child: ElevatedButton(
                                onPressed: (() {
                                  print(
                                      "${ApplicationData.buttons[index]} was pressed");
                                  ApplicationData.currentText =
                                      ApplicationData.buttons[index];
                                  setState(() {
                                    showRecording = true;
                                  });
                                }),
                                style: ButtonStyle(
                                  backgroundColor:
                                      MaterialStateProperty.all<Color>(
                                          Colors.blue),
                                  foregroundColor:
                                      MaterialStateProperty.all<Color>(
                                          Colors.white),
                                  fixedSize: MaterialStateProperty.all<Size>(
                                    const Size(100, 20),
                                  ),
                                  surfaceTintColor:
                                      MaterialStateProperty.all<Color>(
                                          Colors.orange),
                                ),
                                child: Text(ApplicationData.buttons[index]),
                              ),
                            );
                          },
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
    );
  }

  getRecordingApparatus() {
    return ModalProgressHUD(
      inAsyncCall: showSpinner,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              Text(
                "Your Score : $score",
                style: TextStyle(fontSize: 15, fontWeight: FontWeight.bold),
              ),
            ],
          ),
          Text(
            ("On record say '${ApplicationData.currentText}'"),
            style: const TextStyle(
                fontSize: 16,
                color: Colors.red,
                overflow: TextOverflow.ellipsis),
            overflow: TextOverflow.ellipsis,
          ),
          SizedBox(
            height: 100,
            child: Recorder(
              onStop: (path) {
                if (kDebugMode) print('Recorded file path: $path');
                setState(() {
                  audioPath = path;
                });
                transcribeVoice(audioPath);
              },
            ),
          ),
          Container(
            height: 50.0,
            margin: const EdgeInsets.all(10),
            child: ElevatedButton(
              onPressed: () {
                setState(() {
                  showRecording = false;
                });
              },
              style: ButtonStyle(
                  shape: MaterialStateProperty.all<OutlinedBorder>(
                      RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(80.0))),
                  padding: MaterialStateProperty.all<EdgeInsetsGeometry>(
                    const EdgeInsets.all(0.0),
                  )),
              child: Ink(
                decoration: BoxDecoration(
                    gradient: const LinearGradient(
                      colors: [Color(0xff374ABE), Color(0xff64B6FF)],
                      begin: Alignment.centerLeft,
                      end: Alignment.centerRight,
                    ),
                    borderRadius: BorderRadius.circular(30.0)),
                child: Container(
                  constraints: BoxConstraints(maxWidth: 150.0, minHeight: 50.0),
                  alignment: Alignment.center,
                  child: const Text(
                    "Back",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 15,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Future<void> transcribeVoice(String path) async {
    print("transcribe path is " + path);
    setState(() {
      showSpinner = true;
    });
    String content = '';
    setPermissions();
    //Solving google credentials

    final serviceAccount = ServiceAccount.fromString(
        '${(await rootBundle.loadString('assets/serviceAccount.json'))}');

    final speechToText = SpeechToText.viaServiceAccount(serviceAccount);

    final config = RecognitionConfig(
        encoding: AudioEncoding.LINEAR16,
        model: RecognitionModel.command_and_search,
        enableAutomaticPunctuation: true,
        sampleRateHertz: 16000,
        languageCode: 'ar-XA');

    //'ar-XA' 'en-US'

    final audio = await _getAudioContent(path);

    await speechToText.recognize(config, audio).then((value) {
      print("values length is ${value.results.length}");
      setState(() {
        content = value.results
            .map((e) => e.alternatives.first.transcript)
            .join('\n');
      });
    }).whenComplete(() {
      if (kDebugMode) {
        print("Content is $content");
        // final RegExp punctuationPattern =
        //     RegExp(r'[!@#\$%^&*()_+{}\[\]:;<>,.?~\\/-]');
        // content = content.replaceAll(punctuationPattern, '');
        setState(() {
          showSpinner = false;
          result = ApplicationData.currentText == content;
        });

        print("ApplicationData - " + ApplicationData.currentText);
        print("content - " + content);
        print(
            "result - " + (ApplicationData.currentText == content).toString());
        String resultText = '';

        if (result) {
          score += 10;
          resultText = 'Good job, you get 10 points !';
        } else {
          resultText = 'No, didnt match, Try again !';
        }

        final snackBar = SnackBar(
            content: Text(resultText),
            action: SnackBarAction(label: '', onPressed: () {}));
        ScaffoldMessenger.of(context).showSnackBar(snackBar);
      }
    });
  }

  _getAudioContent(String path) async {
    path = Uri.decodeFull(
        path.replaceFirst("file://", "")); // Remove the 'file://' scheme

    return File(path).readAsBytesSync().toList();
  }

  void setPermissions() async {
    await Permission.storage.request();
  }
}

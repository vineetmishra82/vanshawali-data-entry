import 'package:record/record.dart';

class RecordConfig {
  final AudioEncoder encoder;
  final int bitRate;
  final int sampleRate;
  final int numChannels;
  final InputDevice? device;
  final bool autoGain;
  final bool echoCancel;
  final bool noiseSuppress;

  const RecordConfig({
    this.encoder = AudioEncoder.wav,
    this.bitRate = 128000,
    this.sampleRate = 44100,
    this.numChannels = 2,
    this.device,
    this.autoGain = false,
    this.echoCancel = false,
    this.noiseSuppress = false,
  });

  Map<String, dynamic> toMap() {
    return {
      'encoder': encoder.name,
      'bitRate': bitRate,
      'sampleRate': sampleRate,
      'numChannels': numChannels,
      'device': device?.toMap(),
      'autoGain': autoGain,
      'echoCancel': echoCancel,
      'noiseSuppress': noiseSuppress,
    };
  }
}

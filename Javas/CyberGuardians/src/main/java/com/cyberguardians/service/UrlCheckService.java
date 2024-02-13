//package com.cyberguardians.service;
//
//import java.nio.FloatBuffer;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.stereotype.Service;
//
//import ai.onnxruntime.OnnxTensor;
//import ai.onnxruntime.OrtEnvironment;
//import ai.onnxruntime.OrtException;
//import ai.onnxruntime.OrtSession;
//
//@Service
//public class UrlCheckService {
//	
//	private OrtEnvironment environment;
//    private OrtSession session;
//
//    public UrlCheckService() {
//        try {
//            this.environment = OrtEnvironment.getEnvironment();
//            this.session = environment.createSession("/CyberGuardians/src/main/resources/templates/URLModel.onnx");
//        } catch (OrtException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean checkUrl(String url) {
//    	// URL을 입력 텐서로 변환
//        float[] input = preprocessUrl(url);
//
//        try {
//            // 입력 텐서 생성
//            OnnxTensor testInput = OnnxTensor.createTensor(environment, FloatBuffer.wrap(input), new long[]{1, input.length});
//
//            // ONNX 모델에 입력 텐서를 전달하여 예측 수행
//            OrtSession.Result result = session.run(Collections.singletonMap("input", testInput));
//
//            // 예측 결과로부터 악성 여부 판별
//            float[][] scores = (float[][]) result.get(0).getValue();
//            float maliciousScore = scores[0][1]; // 악성일 확률
//            return maliciousScore >= 0.5; // 예측한 확률이 0.5 이상이면 악성으로 판별
//        } catch (OrtException e) {
//            e.printStackTrace();
//            return false; // 오류 발생 시 안전하게 처리
//        }
//    }
//
//    public float[] preprocessUrl(String url) {
//        // URL을 사전 처리하여 모델의 입력 형식으로 변환
//        // 예를 들어, URL을 특성 벡터로 변환하거나, 원-핫 인코딩을 적용하는 등의 전처리 과정을 수행
//        // 이 예시에서는 단순히 URL의 길이를 입력으로 사용
//        return new float[]{(float) url.length()};
//    }
//}

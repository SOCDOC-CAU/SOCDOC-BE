package com.cau.socdoc.repository;

import com.cau.socdoc.domain.User;
import com.cau.socdoc.util.MessageUtil;
import com.cau.socdoc.util.api.ResponseCode;
import com.cau.socdoc.util.exception.UserException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class UserRepositoryImpl implements UserRepository{

    // 회원가입
    @Override
    public String createUser(String userId, String userName, String userEmail, String address1, String address2) {
        Firestore db = FirestoreClient.getFirestore();
        User user = User.of(userId, userName, userEmail, address1, address2);
        db.collection(MessageUtil.COLLECTION_USER).document(userId).set(user);
        return userId;
    }

    // id로 유저명 조회
    @Override
    public String findNameById(String userId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        return db.collection(MessageUtil.COLLECTION_USER).document(userId).get().get().getString("userName");
    }

    // id로 유저 조회
    @Override
    public User findUserById(String userId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(MessageUtil.COLLECTION_USER).document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if(!document.exists()){
            throw new UserException(ResponseCode.USER_NOT_FOUND);
        }
        return document.toObject(User.class);
    }

    // id로 유저 찾아 주소 수정
    @Override
    public void updateUserAddress(String userId, String address1, String address2) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(MessageUtil.COLLECTION_USER).document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if(!document.exists()){
            throw new UserException(ResponseCode.USER_NOT_FOUND);
        }
        docRef.update(MessageUtil.ADDRESS1, address1);
        docRef.update(MessageUtil.ADDRESS2, address2);
    }

    // id로 유저 찾아 이름 수정
    @Override
    public void updateUserName(String userId, String userName) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(MessageUtil.COLLECTION_USER).document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if(!document.exists()){
            throw new UserException(ResponseCode.USER_NOT_FOUND);
        }
        docRef.update(MessageUtil.USER_NAME, userName);
    }

    // 회원탈퇴
    @Override
    public void deleteUser(String userId) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(MessageUtil.COLLECTION_USER).document(userId).delete();
    }
}

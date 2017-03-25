package com.wow.wowmeet.data.chat;

import android.util.Log;

import com.google.gson.Gson;
import com.wow.wowmeet.exceptions.SendMessageFailedException;
import com.wow.wowmeet.utils.Constants;
import com.wow.wowmeet.utils.OkHttpUtils;

import java.net.URISyntaxException;
import java.util.HashMap;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class ChatRepository {

    private static final String MESSAGE_ENDPOINT = Constants.API_URL + "/message";
    private static final String PARAM_TO = "to";
    private static final String PARAM_MESSAGE = "message";

    private static final String SOCKET_ENDPOINT = Constants.API_URL + "/chat";
    private static final String SOCKET_EVENT_JOIN = "join";
    private static final String SOCKET_EVENT_MESSAGE = "message";

    public Single<String> sendMessage(final String message, final String toId, final String token){
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                OkHttpClient okHttpClient = new OkHttpClient();
                HashMap<String, String> fields = new HashMap<>();
                fields.put(PARAM_TO, toId);
                fields.put(PARAM_MESSAGE, message);

                Response response = OkHttpUtils.makePostRequestWithUser
                        (okHttpClient, MESSAGE_ENDPOINT, fields, token);

                String responseBody = response.body().string();
                if(response.isSuccessful()){
                    e.onSuccess(responseBody);
                }else{
                    SendMessageFailedException sendMessageFailedException =
                            new SendMessageFailedException(responseBody);

                    e.onError(sendMessageFailedException);
                }
            }
        });
    }

    public Socket connectToSocket() throws URISyntaxException {
        return IO.socket(SOCKET_ENDPOINT);
    }

    private void joinChannel(Socket socket, String roomId){
        socket.connect();
        socket.emit(SOCKET_EVENT_JOIN, roomId);
    }

    private void emitMessage(Socket socket, String roomId, String message, String token){
        socket.emit(SOCKET_EVENT_MESSAGE, roomId, message);
    }

    private void disconnectFromSocket(Socket socket){
        socket.disconnect();
    }

    public void socketTry(Socket socket, String roomId, String message, String token) throws URISyntaxException {
        joinChannel(socket, roomId);
        emitMessage(socket, roomId, message, token);
    }

    public void socketListen(Socket socket, String roomId){
        socket.on(SOCKET_EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gson gson = new Gson();
                Log.d("SOCKET", args[0].toString());
            }
        });
    }

}

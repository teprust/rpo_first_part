package ru.iu3.fclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import java.util.Random;
import ru.iu3.fclient.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'fclient' library on application startup.
    static {
        System.loadLibrary("fclient");
        System.loadLibrary("mbedcrypto");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int res = initRng();
        byte[] d_key = randomBytes(16);

        Random random = new Random();

        byte[] mas = new byte[20];
        for (int i = 0; i < mas.length; ++i) {
            mas[i] = (byte) ((byte) random.nextInt() % 255);
        }

        // Пример шифрованя данных (в отладчике)
        byte[] encrypt_mas = encrypt(d_key, mas);

        // Пример дешифрования данных (в отладчике)
        byte[] decrypt_mas = decrypt(d_key, encrypt_mas);
        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);

        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'fclient' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public static native int initRng();
    public static native byte[] randomBytes(int no);
    public static native byte[] encrypt(byte[] key, byte[] data);
    public static native byte[] decrypt(byte[] key, byte[] data);
}
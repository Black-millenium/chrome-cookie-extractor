package me.desu.chromecookieextractor.service;

import com.sun.jna.platform.win32.Crypt32Util;
import lombok.AllArgsConstructor;
import me.desu.chromecookieextractor.dto.CookiesDTO;
import me.desu.chromecookieextractor.entity.CookiesEntity;
import me.desu.chromecookieextractor.mapper.CookiesMapper;
import me.desu.chromecookieextractor.repository.CookiesRepository;
import me.desu.chromecookieextractor.utils.Utils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * thx to <a href="https://stackoverflow.com/questions/65939796/java-how-do-i-decrypt-chrome-cookies">stackoverflow</a>
 */
@AllArgsConstructor
@Service
public class ExtractorService {

    private final CookiesRepository cookiesRepository;
    private final CookiesMapper cookiesMapper;

    public List<CookiesDTO> readCookie(String hostKey)
            throws IOException,
            InvalidAlgorithmParameterException,
            NoSuchPaddingException,
            IllegalBlockSizeException,
            NoSuchAlgorithmException,
            BadPaddingException,
            InvalidKeyException {

        final List<CookiesDTO> result = new ArrayList<>();
        final byte[] masterKey = getMasterKey(Utils.localState());
        final List<CookiesEntity> cookies = cookiesRepository.findByHostKeyContains(hostKey);

        for (CookiesEntity cookiesEntity : cookies) {
            CookiesDTO cookiesDTO = cookiesMapper.toDto(cookiesEntity);
            cookiesDTO.setValue(getCookie(masterKey, cookiesEntity.getEncryptedValue()));
            result.add(cookiesDTO);
        }

        return result;
    }

    private byte[] getMasterKey(String path) throws IOException {
        JSONObject jsonObjectLocalState = new JSONObject(Files.readString(Path.of(path)));
        String encryptedMasterKeyWithPrefixB64 = (String) ((JSONObject) jsonObjectLocalState.get("os_crypt")).get("encrypted_key");

        // Remove prefix (DPAPI)
        byte[] encryptedMasterKeyWithPrefix = Base64.getDecoder().decode(encryptedMasterKeyWithPrefixB64);
        byte[] encryptedMasterKey = Arrays.copyOfRange(encryptedMasterKeyWithPrefix, 5, encryptedMasterKeyWithPrefix.length);

        // Decrypt
        return Crypt32Util.cryptUnprotectData(encryptedMasterKey);
    }

    private String getCookie(byte[] masterKey, byte[] encryptedCookie)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        // Separate praefix (v10), nonce and ciphertext/tag
        byte[] nonce = Arrays.copyOfRange(encryptedCookie, 3, 3 + 12);
        byte[] ciphertextTag = Arrays.copyOfRange(encryptedCookie, 3 + 12, encryptedCookie.length);

        // Decrypt
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, nonce);
        SecretKeySpec keySpec = new SecretKeySpec(masterKey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        return new String(cipher.doFinal(ciphertextTag));
    }
}

package com.example.tickr.tickr.common.utils;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TOTPGenerator {

    @Value("${totp.secret}")
    private String secretKey;

    //TODO: Implement TOTP Generation
    public String generateTOTP() throws CodeGenerationException {
        // 2. To generate a TOTP code on the server side (for verification or testing)
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator(HashingAlgorithm.SHA1, 6);

//        long currentTimeSeconds = timeProvider.getTime();
        String totpCode = codeGenerator.generate(secretKey, System.currentTimeMillis() / 1000 / 30);
        System.out.println("Generated TOTP Code: " + totpCode);

        return totpCode;
    }

}

package com.example.proiggimenez.fingerprinttest;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.os.CancellationSignal;

public class BiometricManager extends BiometricManagerV23 {


    BiometricManager(final BiometricBuilder biometricBuilder) {
        this.context = biometricBuilder.context;
        this.title = biometricBuilder.title;
        this.subtitle = biometricBuilder.subtitle;
        this.description = biometricBuilder.description;
        this.negativeButtonText = biometricBuilder.negativeButtonText;
        this.cancellationSignal = biometricBuilder.cancellationSignal;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void authenticate(@NonNull final BiometricCallback biometricCallback) {

        boolean error = false;

        if(title == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog title cannot be null");
            error = true;
        }


        if(subtitle == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog subtitle cannot be null");
            error = true;
        }


        if(description == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog description cannot be null");
            error = true;
        }

        if(negativeButtonText == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog negative button text cannot be null");
            error = true;
        }


        if(!BiometricUtils.isSdkVersionSupported()) {
            biometricCallback.onSdkVersionNotSupported();
            error = true;
        }

        if(!BiometricUtils.isPermissionGranted(context)) {
            biometricCallback.onBiometricAuthenticationPermissionNotGranted();
            error = true;
        }

        if(!BiometricUtils.isHardwareSupported(context)) {
            biometricCallback.onBiometricAuthenticationNotSupported();
            error = true;
        }

        if(!BiometricUtils.isFingerprintAvailable(context)) {
            biometricCallback.onBiometricAuthenticationNotAvailable();
            error = true;
        }

        if(!error)
            displayBiometricDialog(biometricCallback);
    }



    private void displayBiometricDialog(BiometricCallback biometricCallback) {
        displayBiometricPromptV23(biometricCallback);
    }


    public static class BiometricBuilder {

        private String title;
        private String subtitle;
        private String description;
        private String negativeButtonText;
        private CancellationSignal cancellationSignal;

        private Context context;
        BiometricBuilder(Context context) {
            this.context = context;
        }

        public BiometricBuilder setTitle(@NonNull final String title) {
            this.title = title;
            return this;
        }

        public BiometricBuilder setSubtitle(@NonNull final String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public BiometricBuilder setDescription(@NonNull final String description) {
            this.description = description;
            return this;
        }

        public BiometricBuilder setNegativeButtonText(@NonNull final String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }

        public BiometricBuilder setCancellationSignal(@NonNull final CancellationSignal cancellationSignal) {
            this.cancellationSignal = cancellationSignal;
            return this;
        }

        public BiometricManager build() {
            return new BiometricManager(this);
        }
    }
}




jglick
on Thu Jun 23 2022

offa
on Thu Jul 01 2021

jglick
on Wed Nov 04 2020

Ilya Gulyaev
on Sat Oct 24 2020

jeffret-b
on Sat Dec 21 2019

jeffret-b
on Mon Dec 02 2019

basil
on Sun Oct 20 2019

Raihaan Shouhell
on Wed Sep 04 2019

jsoref
on Thu Feb 21 2019

stephenc
on Wed Jul 27 2016

stephenc
on Tue Jul 26 2016

stephenc
on Tue Jul 26 2016
[ENKINS-36923] Make the type safety easier to use

stephenc
on Tue Jul 26 2016

stephenc
on Tue Jul 26 2016
package jenkins.model.identity;

import hudson.Extension;
import hudson.model.UnprotectedRootAction;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

/**
 * A simple root action that exposes the public key to users so that they do not need to search for the
 * {@code X-Instance-Identity} response header, also exposes the fingerprint of the public key so that people
 * can verify a fingerprint of a master before connecting to it.
 *
 * @since FIXME
 */
@Extension
public class IdentityRootAction implements UnprotectedRootAction {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getIconFileName() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUrlName() {
        return InstanceIdentityProvider.get(InstanceIdentityProvider.RSA) == null
                ? null : "instance-identity";
    }

    /**
     * Returns the PEM encoded public key.
     *
     * @return the PEM encoded public key.
     */
    public String getPublicKey() {
        InstanceIdentityProvider<RSAPublicKey, RSAPrivateKey> provider =
                InstanceIdentityProvider.get(InstanceIdentityProvider.RSA);
        RSAPublicKey key = provider == null ? null : provider.getPublicKey();
        if (key == null) {
            return null;
        }
        byte[] encoded = Base64.encodeBase64(key.getEncoded());
        int index = 0;
        StringBuilder buf = new StringBuilder(encoded.length + 20);
        while (index < encoded.length) {
            int len = Math.min(64, encoded.length - index);
            if (index > 0) {
                buf.append("\n");
            }
            buf.append(new String(encoded, index, len, Charsets.UTF_8));
            index += len;
        }
        return String.format("-----BEGIN PUBLIC KEY-----%n%s%n-----END PUBLIC KEY-----%n", buf.toString());
    }

    /**
     * Returns the fingerprint of the public key.
     *
     * @return the fingerprint of the public key.
     */
    public String getFingerprint() {
        InstanceIdentityProvider<RSAPublicKey, RSAPrivateKey> provider =
                InstanceIdentityProvider.get(InstanceIdentityProvider.RSA);
        RSAPublicKey key = provider == null ? null : provider.getPublicKey();
        if (key == null) {
            return null;
        }
        // TODO replace with org.jenkinsci.remoting.util.KeyUtils once JENKINS-36871 changes are merged
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            byte[] bytes = digest.digest(key.getEncoded());
            StringBuilder result = new StringBuilder(Math.max(0, bytes.length * 3 - 1));
            for (int i = 0; i < bytes.length; i++) {
                if (i > 0) {
                    result.append(':');
                }
                int b = bytes[i] & 0xFF;
                result.append(Character.forDigit((b>>4)&0x0f, 16)).append(Character.forDigit(b&0xf, 16));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("JLS mandates MD5 support");
        }
    }
}
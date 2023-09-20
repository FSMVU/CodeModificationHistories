


jglick
on Thu Jun 23 2022

basil
on Mon Jan 03 2022

basil
on Sat Sep 18 2021

jtnord
on Thu Mar 26 2020

jtnord
on Thu Mar 26 2020

stephenc
on Wed Jul 27 2016

stephenc
on Wed Jul 27 2016

stephenc
on Tue Jul 26 2016

stephenc
on Tue Jul 26 2016

stephenc
on Tue Jul 26 2016

stephenc
on Tue Jul 26 2016
[JENKINS-36923] Documentation tweaks

stephenc
on Tue Jul 26 2016

stephenc
on Tue Jul 26 2016
/*
 * The MIT License
 *
 * Copyright (c) 2016, CloudBees Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jenkins.model.identity;

import hudson.ExtensionList;
import hudson.ExtensionPoint;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

/**
 * A source of instance identity.
 *
 * @param <PUB>  the type of public key.
 * @param <PRIV> the type of private key.
 * @since FIXME
 */
public abstract class InstanceIdentityProvider<PUB extends PublicKey, PRIV extends PrivateKey> implements
        ExtensionPoint {
    /**

     * Gets the {@link KeyPair} that comprises the instance identity.
     *
     * @return the {@link KeyPair} that comprises the instance identity. {@code null} could technically be returned in
     * the event that a keypair could not be generated, for example if the specific key type of this provider
     * is not permitted at the required length by the JCA policy.
     */
    @CheckForNull
    public abstract KeyPair getKeyPair();

    /**
     * Shortcut to {@link KeyPair#getPublic()}.
     *
     * @return the public key. {@code null} if {@link #getKeyPair()} is {@code null}.
     */
    @CheckForNull
    public PUB getPublicKey() {
        KeyPair keyPair = getKeyPair();
        return keyPair == null ? null : (PUB) keyPair.getPublic();
    }

    /**
     * Shortcut to {@link KeyPair#getPrivate()}.
     *
     * @return the private key. {@code null} if {@link #getKeyPair()} is {@code null}.
     */
    @CheckForNull
    public PRIV getPrivateKey() {
        KeyPair keyPair = getKeyPair();
        return keyPair == null ? null : (PRIV) keyPair.getPrivate();
    }

    /**
     * Gets the self-signed {@link X509Certificate} that is associated with this identity. The certificate
     * will must be currently valid. Repeated calls to this method may result in new certificates being generated.
     *
     * @return the certificate. {@code null} if {@link #getKeyPair()} is {@code null}.
     */
    @CheckForNull
    public abstract X509Certificate getCertificate();

    /**
     * Gets the provider of the required identity type.
     *
     * @param keyType the type of private key.
     * @param <PUB>   the type of public key.
     * @param <PRIV>  the type of private key.
     * @return the provider or {@code null} if no provider of the specified type is available.
     */
    @CheckForNull
    @SuppressWarnings("unchecked")
    public static <PUB extends PublicKey, PRIV extends PrivateKey> InstanceIdentityProvider<PUB, PRIV> get(
            Class<PRIV> keyType) {
        for (InstanceIdentityProvider provider : ExtensionList.lookup(InstanceIdentityProvider.class)) {
            KeyPair keyPair = provider.getKeyPair();
            if (keyPair != null && keyType.isInstance(keyPair.getPrivate())) {
                return (InstanceIdentityProvider<PUB, PRIV>) provider;
            }
        }
        return null;
    }





}
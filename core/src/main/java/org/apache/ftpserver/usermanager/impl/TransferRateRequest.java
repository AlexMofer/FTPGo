/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ftpserver.usermanager.impl;

import org.apache.ftpserver.ftplet.AuthorizationRequest;

/**
 * <strong>Internal class, do not use directly.</strong>
 * <p>
 * Request for getting the maximum allowed transfer rates for a user
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class TransferRateRequest implements AuthorizationRequest {

    private int maxDownloadRate = 0;

    private int maxUploadRate = 0;

    /**
     * @return the maxDownloadRate
     */
    public int getMaxDownloadRate() {
        return maxDownloadRate;
    }

    /**
     * @param maxDownloadRate the maxDownloadRate to set
     */
    public void setMaxDownloadRate(int maxDownloadRate) {
        this.maxDownloadRate = maxDownloadRate;
    }

    /**
     * @return the maxUploadRate
     */
    public int getMaxUploadRate() {
        return maxUploadRate;
    }

    /**
     * @param maxUploadRate the maxUploadRate to set
     */
    public void setMaxUploadRate(int maxUploadRate) {
        this.maxUploadRate = maxUploadRate;
    }

}

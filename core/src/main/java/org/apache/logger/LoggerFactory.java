/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.logger;

/**
 * LoggerFactory
 * Created by Alex on 2018/1/19.
 */
public class LoggerFactory {

    private static LoggerGenerator GENERATOR = new EmptyLoggerGenerator();


    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    public static Logger getLogger(String name) {
        return GENERATOR.create(name);
    }

    @SuppressWarnings("unused")
    public static void setLoggerGenerator(LoggerGenerator generator) {
        if (generator == null)
            return;
        GENERATOR = generator;
    }

    private static class EmptyLoggerGenerator implements LoggerGenerator {
        private static Logger LOGGER = new EmptyLogger();

        @Override
        public Logger create(String tag) {
            return LOGGER;
        }
    }
}

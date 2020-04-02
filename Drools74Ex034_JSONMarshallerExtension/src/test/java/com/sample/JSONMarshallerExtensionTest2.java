/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.sample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.junit.Test;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;

public class JSONMarshallerExtensionTest2 {
    private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Test
    public void testInsertObjectCommand() {
        Set<Class<?>> extraClasses = new HashSet<Class<?>>();
        extraClasses.add(GregorianCalendar.class);
        Marshaller marshaller = MarshallerFactory.getMarshaller(extraClasses, MarshallingFormat.JSON, this.getClass().getClassLoader());
        

        
        Calendar calendar = GregorianCalendar.getInstance();
        
        InsertObjectCommand command = new InsertObjectCommand(calendar);
        
        String marshall = marshaller.marshall(command);
        
        System.out.println("marshall = " + marshall);
        
        //assertEquals(marshall, "\""+ FORMATTER.format(calendar.getTime()) +"\"" );
        
        InsertObjectCommand unmarshall = marshaller.unmarshall(marshall, InsertObjectCommand.class);
        
        System.out.println("unmarshall = " + unmarshall);
        
        Object object = unmarshall.getObject();
        
        System.out.println("object = " + object + ", object.getClass() = " + object.getClass());
        
//        assertEquals(unmarshall, calendar);
    }
}

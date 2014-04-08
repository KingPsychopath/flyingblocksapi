/**
 * flyingblocksapi Copyright (C) 2014 ase34 and contributors
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
/*
 * This code is pulled from Jogy34 (https://forums.bukkit.org/threads/tutorial-1-7-creating-a-custom-entity.212849/)
 */
package de.ase34.flyingblocksapi.natives.v1_6_R3.util;

import java.lang.reflect.Field;
import java.util.Map;

public class EntityRegistrator {

    protected static Field mapStringToClassField, mapClassToStringField, mapClassToIdField,
            mapStringToIdField, mapIdToClassField;

    static {
        try {
            mapStringToClassField = net.minecraft.server.v1_6_R3.EntityTypes.class
                    .getDeclaredField("c");
            mapClassToStringField = net.minecraft.server.v1_6_R3.EntityTypes.class
                    .getDeclaredField("d");
            mapIdToClassField = net.minecraft.server.v1_6_R3.EntityTypes.class.getDeclaredField("e");
            mapClassToIdField = net.minecraft.server.v1_6_R3.EntityTypes.class
                    .getDeclaredField("f");

            mapStringToClassField.setAccessible(true);
            mapClassToStringField.setAccessible(true);
            mapIdToClassField.setAccessible(true);
            mapClassToIdField.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void registerCustomEntity(Class entityClass, String name, int id) {
        if (mapStringToClassField == null || mapStringToIdField == null
                || mapClassToStringField == null || mapClassToIdField == null) {
            return;
        } else {
            try {
                Map mapStringToClass = (Map) mapStringToClassField.get(null);
                Map mapStringToId = (Map) mapStringToIdField.get(null);
                Map mapClasstoString = (Map) mapClassToStringField.get(null);
                Map mapClassToId = (Map) mapClassToIdField.get(null);

                mapStringToClass.put(name, entityClass);
                mapStringToId.put(name, Integer.valueOf(id));
                mapClasstoString.put(entityClass, name);
                mapClassToId.put(entityClass, Integer.valueOf(id));

                mapStringToClassField.set(null, mapStringToClass);
                mapStringToIdField.set(null, mapStringToId);
                mapClassToStringField.set(null, mapClasstoString);
                mapClassToIdField.set(null, mapClassToId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

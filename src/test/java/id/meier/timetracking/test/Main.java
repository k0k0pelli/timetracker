/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.meier.timetracking.test;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Topic[] topics = new Topic[7];
        for (int i = 0; i < 7;i++) {
            topics[0] = new Topic("" + ((char)65 + i));
        }
        Person[] persons = new Person[21];
        for (int i = 0; i < 21; i++) {
            persons[i] = new Person(Integer.toString(i + 1), topics[i / 3]);
        }

        Arrays.stream(persons).forEach(p -> System.out.println(p.toString()));

    }
}

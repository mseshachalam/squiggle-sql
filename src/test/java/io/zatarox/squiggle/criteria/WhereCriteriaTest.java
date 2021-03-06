/*
 * Copyright 2004-2015 Joe Walnes, Guillaume Chauvet.
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
package io.zatarox.squiggle.criteria;

import io.zatarox.squiggle.SelectQuery;
import io.zatarox.squiggle.Table;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.hamcrest.text.IsEqualIgnoringWhiteSpace;

public class WhereCriteriaTest {

    @Test
    public void whereCriteria() {
        Table people = new Table("people");

        SelectQuery select = new SelectQuery();

        select.addColumn(people, "firstname");
        select.addColumn(people, "lastname");

        select.addCriteria(
                new MatchCriteria(people, "height", MatchCriteria.GREATER, 1.8));
        select.addCriteria(
                new InCriteria(people, "department", new String[]{"I.T.", "Cooking"}));
        select.addCriteria(
                new BetweenCriteria(people.getColumn("age"), 18, 30));

        assertThat(select.toString(), IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(
                "SELECT "
                + "    people.firstname , "
                + "    people.lastname "
                + "FROM "
                + "    people "
                + "WHERE "
                + "    people.height > 1.8 AND "
                + "    people.department IN ( "
                + "        'I.T.', 'Cooking' "
                + "    ) AND"
                + "    people.age BETWEEN 18 AND 30"));

    }

    @Test
    public void nullCriteria() {
        Table people = new Table("people");

        SelectQuery select = new SelectQuery();

        select.addToSelection(people.getWildcard());

        select.addCriteria(
                new IsNullCriteria(people.getColumn("name")));
        select.addCriteria(
                new IsNotNullCriteria(people.getColumn("age")));

        assertThat(select.toString(), IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(
                "SELECT "
                + "    people.* "
                + "FROM "
                + "    people "
                + "WHERE "
                + "    people.name IS NULL AND "
                + "    people.age IS NOT NULL"));
    }

    @Test
    public void betweenCriteriaWithColumns() {
        Table rivers = new Table("rivers");

        SelectQuery select = new SelectQuery();

        select.addColumn(rivers, "name");
        select.addColumn(rivers, "level");

        select.addCriteria(
                new BetweenCriteria(rivers.getColumn("level"), rivers.getColumn("lower_limit"), rivers.getColumn("upper_limit")));

        assertThat(select.toString(), IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(
                "SELECT "
                + "    rivers.name , "
                + "    rivers.level "
                + "FROM "
                + "    rivers "
                + "WHERE "
                + "    rivers.level BETWEEN rivers.lower_limit AND rivers.upper_limit"));
    }
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.storm.sql.planner.streams.rules;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.core.Calc;
import org.apache.calcite.rel.logical.LogicalCalc;
import org.apache.storm.sql.planner.streams.rel.StreamsCalcRel;
import org.apache.storm.sql.planner.streams.rel.StreamsLogicalConvention;

public class StreamsCalcRule extends ConverterRule {
    public static final StreamsCalcRule INSTANCE = new StreamsCalcRule();

    private StreamsCalcRule() {
        super(LogicalCalc.class, Convention.NONE, StreamsLogicalConvention.INSTANCE, "StreamsCalcRule");
    }

    @Override
    public RelNode convert(RelNode rel) {
        final Calc calc = (Calc) rel;
        final RelNode input = calc.getInput();

        return new StreamsCalcRel(calc.getCluster(), calc.getTraitSet().replace(StreamsLogicalConvention.INSTANCE),
                                  convert(input, input.getTraitSet().replace(StreamsLogicalConvention.INSTANCE)),
                                  calc.getProgram());
    }
}

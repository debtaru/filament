/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.google.android.filament.tungsten.compiler

import com.google.android.filament.tungsten.model.Graph
import com.google.android.filament.tungsten.model.createAdderNode
import com.google.android.filament.tungsten.model.createFloat3ConstantNode
import com.google.android.filament.tungsten.model.createShaderNode
import org.junit.Test

class GraphCompilerTest {

    /**
     * For now, we just check that the graphs can compile without throwing any errors.
     * TODO: add more robust checks
     */

    @Test
    fun test_compileSingleNode() {
        val graph = Graph(nodes = listOf(createAdderNode("0")))
        val compiler = GraphCompiler(graph)
        compiler.compileGraph()
    }

    @Test
    fun test_compileTwoNodes() {
        val constantNodeA = createFloat3ConstantNode("0")
        val constantNodeB = createFloat3ConstantNode("1")
        val adderNode = createAdderNode("2")
        val shaderNode = createShaderNode("3")
        val graph = Graph(
                nodes = listOf(shaderNode, adderNode, constantNodeA, constantNodeB),
                rootNodeId = shaderNode.id,
                connections = listOf(
                        adderNode.getOutputSlot("result") to shaderNode.getInputSlot("baseColor"),
                        constantNodeA.getOutputSlot("result") to adderNode.getInputSlot("a"),
                        constantNodeB.getOutputSlot("result") to adderNode.getInputSlot("b")
                )
        )

        val compiler = GraphCompiler(graph)
        compiler.compileGraph()
    }
}
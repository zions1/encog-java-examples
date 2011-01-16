/*
 * Encog(tm) Examples v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.examples.neural.xorradial;

import org.encog.mathutil.rbf.RBFEnum;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.neural.pattern.RadialBasisPattern;
import org.encog.util.logging.Logging;
import org.encog.util.simple.EncogUtility;

/**
 * XOR: This example is essentially the "Hello World" of neural network
 * programming. This example shows how to construct an Encog neural network to
 * predict the output from the XOR operator using a RBF neural network. This
 * example uses RPROP to train.
 */
public class XorRadial {

	public static double XOR_INPUT[][] = { { 0.0, 0.0 }, { 1.0, 0.0 },
			{ 0.0, 1.0 }, { 1.0, 1.0 } };

	public static double XOR_IDEAL[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };

	public static void main(final String args[]) {

		Logging.stopConsoleLogging();

		RadialBasisPattern pattern = new RadialBasisPattern();
		pattern.setInputNeurons(2);
		pattern.addHiddenLayer(4);
		pattern.setOutputNeurons(1);
		BasicNetwork network = (BasicNetwork)pattern.generate();
		RadialBasisFunctionLayer rbfLayer = (RadialBasisFunctionLayer)network.getLayer(RadialBasisPattern.RBF_LAYER);

		rbfLayer.randomizeRBFCentersAndWidths(2, 0, 1, RBFEnum.Gaussian);
		network.getStructure().finalizeStructure();

		final NeuralDataSet trainingSet = new BasicNeuralDataSet(
				XorRadial.XOR_INPUT, XorRadial.XOR_IDEAL);

		// train the neural network
		EncogUtility.trainToError(network, trainingSet, 0.01);

		// test the neural network
		System.out.println("Neural Network Results:");
		EncogUtility.evaluate(network, trainingSet);
	}
}

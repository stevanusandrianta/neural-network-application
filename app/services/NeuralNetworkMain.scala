package services

/**
  * Created by stevanusandrianta on 4/21/16.
  */

import java.util.Calendar

object NeuralNetworkMain{

  lazy val trainedNetwork = NeuralNetwork.loadNetwork("model/network.json")

  def classify(network: Network, data: List[Double]) = {
    //val trainedNetwork = NeuralNetwork.loadNetwork
    NeuralNetwork.feedForward(network, data)
  }

}


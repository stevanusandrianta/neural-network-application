package services

import java.io.File

/**
  * Created by stevanus.andrianta on 5/11/2016.
  */

/*
object Validation {

  def splitValidation(data: List[List[Double]], learningRate: Double, momentum: Double,
                      maxIteration: Int, minMSE: Double, nInput: Int, nHidden: List[Int], nOutput: Int,
                      weightGenerator: WeightGeneratorTrait, activationFunction: ActivationFunctionTrait,
                      trainingPercentage: Double){

    val splittedData = data.splitAt((trainingPercentage * data.size).round.toInt)



    val network = NeuralNetwork.initiateNetwork(splittedData._1, learningRate, nInput, nHidden, nOutput, weightGenerator, activationFunction)
    //val trainedNetwork = NeuralNetwork.initiateTraining(network, splittedData._1, learningRate, momentum, maxIteration, minMSE)

    var trainedNetwork = network
    val file = new File("weight5.json")
    if(!file.exists()) {
      trainedNetwork = NeuralNetwork.initiateTraining(network, splittedData._1, learningRate, momentum, maxIteration, minMSE)
      NeuralNetwork.saveNetwork(trainedNetwork)
    }else{
      trainedNetwork = NeuralNetwork.loadNetwork
    }

    val Trainingclassified = splittedData._1.zipWithIndex.map { item =>
      println(s"expected : ${item._1.last}, prediction : ${NeuralNetwork.feedForward(trainedNetwork, item._1).head}")
      if (item._1.last == NeuralNetwork.feedForward(trainedNetwork, item._1).head.round.toInt) true else false
    }

    val Testingclassified = splittedData._2.zipWithIndex.map { item =>
      println(s"expected : ${item._1.last}, prediction : ${NeuralNetwork.feedForward(trainedNetwork, item._1).head}")
      if (item._1.last == NeuralNetwork.feedForward(trainedNetwork, item._1).head.round.toInt) true else false
    }

    println("training accuracy")
    println(s"correct classified : ${Trainingclassified.count(_ == true)}")
    println(s"false classified : ${Trainingclassified.count(_ == false)}")
    println(s"accuracy : ${(Trainingclassified.count(_ == true).toDouble / Trainingclassified.size.toDouble) * 100}%")

    println("testing accuracy")
    println(s"correct classified : ${Testingclassified.count(_ == true)}")
    println(s"false classified : ${Testingclassified.count(_ == false)}")
    println(s"accuracy : ${(Testingclassified.count(_ == true).toDouble / Testingclassified.size.toDouble) * 100}%")

    NeuralNetwork.saveNetwork(network)

  }
  */

  /*def crossValidation(data: List[List[Double]], group: Int, learningRate : Double, nInput : Int, nHidden : Int, nOutput : Int, maxIter: Int) = {
    val splitter : Double = data.size / group.toDouble
    (1 to group).map{ i =>
      val start = ((i-1)*splitter).round.toInt
      val end = (i*splitter).round.toInt
      val sequences = (start until end).toList
      println(sequences)

      val trainingData = data.zipWithIndex.filterNot(item => sequences.contains(item._2)).map(_._1)
      val testData = data.zipWithIndex.filter(item => sequences.contains(item._2)).map(_._1)

      println(trainingData)

      val weight = BackPropagation.trainBPNN(trainingData, learningRate, nInput, nHidden, nOutput, maxIter)

      val comparedData = testData.zipWithIndex.map{ item =>
        val expectedValue = item._1.last
        val classificationValue = BackPropagation.classifyBPNN(weight._1, weight._2, item._1).round.toDouble
        if(expectedValue == classificationValue) true else false
      }

      val trueClassification = comparedData.filter(_ == true).size
      val falseClassification = comparedData.filter(_ == false).size
      println(s"iteration number : $i, total data : ${testData.size}, correct : $trueClassification, false : $falseClassification")

    }
  }*/

/*
}*/

import qupath.ext.stardist.StarDist2D

// Specify the model file (you will need to change this!)
var pathModel = '/path/to/dsb2018_heavy_augment.pb'
var stardist = StarDist2D.builder(pathModel)
      .threshold(0.5)     // Prediction threshold
      .preprocess(        // Extra preprocessing steps, applied sequentially
              ImageOps.Core.subtract(100),
              ImageOps.Core.divide(100)
      )
//      .normalizePercentiles(1, 99) // Percentile normalization (turned off here)
      .pixelSize(0.5)                // Resolution for detection
      .includeProbability(true)      // Include prediction probability as measurement
      .build()
      
var imageData = getCurrentImageData()
var pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}
stardist.detectObjects(imageData, pathObjects)
println 'Done!'
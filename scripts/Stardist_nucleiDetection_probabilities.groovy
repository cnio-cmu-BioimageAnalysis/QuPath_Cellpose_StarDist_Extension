import qupath.ext.stardist.StarDist2D

// Specify the model file (you will need to change this!)
var pathModel = '/path/to/he_heavy_augment.pb'

var stardist = StarDist2D.builder(pathModel)
        .threshold(0.1)              // Prediction threshold
        .normalizePercentiles(1, 99) // Percentile normalization
        .pixelSize(0.5)              // Resolution for detection
        .includeProbability(true)    // Include prediction probability as measurement
        .build()

// Run detection for the selected objects
var imageData = getCurrentImageData()
var pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}
stardist.detectObjects(imageData, pathObjects)
println 'Done!'
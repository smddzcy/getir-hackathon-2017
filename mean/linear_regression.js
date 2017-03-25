var sylvester = require('sylvester'),
  Matrix = sylvester.Matrix,
  Vector = sylvester.Vector;
var _ = require('underscore');

var LinearRegression = function(options) {
  this.options = options || {};
  if (this.options.algorithm === 'GradientDescent') {
    this.algorithm = 'GradientDescent';
    this.saveCosts = options.saveCosts || false;
    this.costs = [];
  } else if (this.options.algorithm === 'NormalEquation') {
    this.algorithm = 'NormalEquation';
  } else {
    this.algorithm = 'NormalEquation';
  }

  this.trained = false;
  this.normalized = false;
};

LinearRegression.prototype.train = function(X, Y, callback) {
  if (!callback || typeof callback !== 'function') {
    callback = function() {}
  }

  if (X && !Array.isArray(X)) {
    throw new Error('X must be an array');
  }

  if (Y && !Array.isArray(Y)) {
    throw new Error('Y must be an array');
  }

  this.X = (this.X ? this.X : []).concat([ X ]);
  this.Y = (this.Y ? this.Y : []).concat([ Y ]);

  if (this.X.length === 0) {
    return callback(new Error('X is empty'));
  } else if (this.Y.length === 0) {
    return callback(new Error('Y is empty'));
  }

  if (this.X.length !== this.Y.length) {
    return callback(new Error('X and Y must be of the same length'));
  }

  // If there is only one point, just choose a slope of 0 and y-intercept of y
  if (this.X.length === 1) {
    this.theta = $M([0, this.Y[0]]);
    this.trained = true;
    return callback();
  }

  if (this.algorithm === 'GradientDescent') {
    return this.trainWithGradientDescent(callback);
  } else {
    return this.trainWithNormalEquation(callback);
  }
};

LinearRegression.addColumnOne = function(X) {
  var zeros = Matrix.Zero(X.length, 1);
  var ones = zeros.add(1);
  var x = ones.augment($M(X));
  return x;
};

function getMaxOfArray(numArray) {
  return Math.max.apply(null, numArray);
}

function getMinOfArray(numArray) {
  return Math.min.apply(null, numArray);
}

LinearRegression.prototype.normalize = function(X) {
  var nbrOfFeatures = X.dimensions().cols;
  var m = X.dimensions().rows;
  var newX = Matrix.Zero(X.dimensions().rows, 1).add(1);
  this.means = [];
  this.ranges = [];
  for (var i = 2; i <= nbrOfFeatures; i++) {
    var feature = X.column(i);
    var sum = _.reduce(feature.elements, function(memo, num) { return memo + num; }, 0);
    var mean = sum / m;
    this.means.push(mean);
    var featureArray = feature.elements;
    var range = getMaxOfArray(featureArray) - getMinOfArray(featureArray);
    var normalizedFeature;
    this.ranges.push(range);
    if (range === 0) {
      normalizedFeature = feature.subtract(mean);
    } else {
      normalizedFeature = feature.subtract(mean).multiply(1 / range);
    }
    newX = newX.augment(normalizedFeature);
  }
  return newX;
};

LinearRegression.prototype.trainWithNormalEquation = function(callback) {
  var x = LinearRegression.addColumnOne(this.X);
  // Build the y matrix
  var y = $M(this.Y);

  // now we can apply the normal equation:
  // see formula at http://upload.wikimedia.org/math/2/c/e/2ce21b8e24ea7509a3295c3acd2ae0ea.png
  var inversePortion = x.transpose().x(x).inverse();
  if (inversePortion) {
    this.theta = inversePortion.x(x.transpose()).x(y);
    this.trained = true;
    return callback();
  } else {
    return callback(new Error('could not inverse the matrix in normal equation. Try to use Gradient Descent instead.'));
  }
};

LinearRegression.computeCost = function(X, Y, theta) {
  var m = Y.dimensions().rows;
  var xThetaMinusY = (X.x(theta)).subtract(Y);
  var xThetaMinusYSquared = _.flatten(xThetaMinusY.elements).map(function(val) { return val * val; });
  var sum = _.reduce(xThetaMinusYSquared, function(memo, num) { return memo + num; }, 0);
  return (1 / (2 * m)) * sum;
};

LinearRegression.prototype.gradientDescent = function(X, Y, theta, learningRate, numberOfIterations) {
  var m = Y.dimensions().rows;
  var nbrOfFeatures = X.dimensions().cols;

  var normalizedX = this.normalize(X);

  for (var i = 0; i < numberOfIterations; i++) {
    var xThetaMinusY = (normalizedX.x(theta)).subtract(Y);
    var tempArray = [];

    for (var j = 1; j <= nbrOfFeatures; j++) {
      var xThetaMinusYTimesXj = xThetaMinusY.transpose().x(normalizedX.column(j));
      var arrayToSum = _.flatten(xThetaMinusYTimesXj.elements);
      var sum = _.reduce(arrayToSum, function(memo, num) { return memo + num; }, 0);

      var temp = theta.e(j, 1) - (learningRate / m) * sum;
      tempArray.push([temp]);
    }
    theta = $M(tempArray);
    if (this.saveCosts) {
      this.costs.push(LinearRegression.computeCost(normalizedX, Y, theta));
    }
  }
  return theta;
};

LinearRegression.prototype.trainWithGradientDescent = function(callback) {
  var learningRate = this.options.learningRate || 0.1;
  var numberOfIterations = this.options.numberOfIterations || 8500;

  // Build the matrix of input features
  var x = LinearRegression.addColumnOne(this.X);
  var nbrOfFeatures = x.dimensions().cols;

  // Build the y matrix
  var y = $M(this.Y);

  // Initialize theta to zero
  this.theta = Matrix.Zero(nbrOfFeatures, 1);

  this.theta = this.gradientDescent(x, y, this.theta, learningRate, numberOfIterations);
  this.normalized = true;
  this.trained = true;
  return callback();
};

LinearRegression.prototype.predict = function(input) {
  var self = this;
  if (self.trained) {
    if (!Array.isArray(input)) {
      input = [input];
    }

    if (self.normalized) {
      input = input.map(function(val, index) {
        return (val - self.means[index]) / self.ranges[index];
      });
    }

    var xInput = $V([1]).augment(input);
    var output = self.theta.transpose().x(xInput);
    return output.e(1, 1);
  } else {
    throw new Error('Cannot predict before training');
  }
};

/**
 * Serialize the model as JSON.
 */
LinearRegression.prototype.toJSON = function() {
  return JSON.stringify({
    theta: this.theta,
    trained: this.trained,
    X: this.X,
    Y: this.Y,
    costs: this.costs,
    algorithm: this.algorithm,
    saveCosts: this.saveCosts,
    costs: this.costs,
    options: this.options
  });
};

/**
 * Deserialize the JSON.
 */
LinearRegression.fromJSON = function(json) {
  var lr = new LinearRegression(json.options);
  lr.theta = json.theta;
  lr.trained = json.trained;
  lr.X = json.X;
  lr.Y = json.Y;
  lr.costs = json.costs;
  lr.algorithm = json.algorithm;
  lr.saveCosts = json.saveCosts;
  lr.costs = json.costs;
  return lr;
}

exports.LinearRegression = LinearRegression;

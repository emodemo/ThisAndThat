#%%
import os
# from statistics import mean

import numpy as np
import matplotlib.pyplot as plt
from astropy.io import fits
from astropy.coordinates import SkyCoord
from astropy import units as u

#set PYTHONPATH=C:\worksemo\ThisAndThat\data_drive_astronomy

# WEEK 1
def max_idx(file='data/image0.fits'):
    # open file and get data
    data = fits.open(file)[0].data
    # argmax - max index along each axis, axis=None as if it was a list
    # axis=0 are columns, axis=1 are rows and so on
    # unravel_index will give input arg in the specified shape (e.g. linear index 14 may be (3,2) in 2D 
    argmax = np.argmax(data)
    idx = np.unravel_index(argmax, data.shape)
    return idx

def mean_fits(files):
    data_sum = sum(fits.open(file)[0].data for file in files)
    # data_sum.shape will be 2D array (200,200) in this case
    return data_sum/len(files)

data_fits = mean_fits(['data/' + file for file in ['image0.fits', 'image1.fits', 'image2.fits']])
plt.imshow(data_fits.T, cmap=plt.cm.viridis)
plt.xlabel('x-pixels (RA)')
plt.ylabel('y-pixels (Dec)')
plt.colorbar()
plt.show()
print(data_fits[100,100])  #print the one in the center

# approximate running median, with binapprox algorithm. 
# Ryan J. Tibshirani, (2008), Fast Computation of the Median by Successive Binning
# It looks for the meadian only within 1SD from the mean.
# It is accurate only to SD/nbins, so keep B nbins large
# when done for many files, mean and std should be taken from all files
# when done for many files, 
def median_bins(data, nbins):
    # returns mean, std, skipped_values, bins, width
    mean = np.mean(data)
    std = np.std(data)
    minval, maxval = mean - std, mean + std
    width = 2*std/nbins # width of the bin
    skipped = 0 # np.zeros((200,200)) for many files
    bins = np.zeros(nbins) # np.zeros((200,200,nbins)) for many files
    for item in data: # for file, for row, for col, when many files
        if item < minval : skipped += 1
        elif item < maxval : 
            idx = int((item - minval) // width)
            bins[idx] += 1
        # the ones bigger than the max are skipped too
    return mean, std, skipped, bins, width

def median_approximate(data, nbins):
    mean, std, skipped, bins, width = median_bins(data, nbins)
    half_data_length = (len(data) + 1) / 2
    i, total = 0, skipped
    for i, b in enumerate(bins): # for i, for j, for k,b in enumerate(bins[i,j])
        total += b
        if total >= half_data_length: break
    return (mean - std) + width*(i + 0.5)
    #median[i, j] = minval[i, j] + width[i, j]*(k + 0.5)


#%%
import os
# from statistics import mean

import numpy as np
import matplotlib.pyplot as plt
from astropy.io import fits
from astropy.coordinates import SkyCoord
from astropy import units as u

# WEEK 2
# Positions of astronomical objects are usually recorded in either "equatorial" or "Galactic" coordinates.
# "Equatorial" are fixed relative to celestial sphere, so positions are independent of when/where observation took place.
# They are defined relative to celestial equator and the eliptic (path fo the sun throughout the year).
# A point is given by 2 coordinates (see right_ascension_and_declination.gif):
#   right ascension : angle from vernal equinox to the point going east to celestial equator
#           Often given in "hms", as it was convenient to calculate when a star will appear in the horizon.
#           A full circle is 24h => 1h = 15 degrees
#   declination     : angle from celestial equator to the point going north (negative for south)
#           Often given in degrees, arcminutes, arcsecond.
#           A full circle is 360 degrees, each degree has 60 arcminutes and 3600 arcseconds.
#           A minute in HMS = 15 arcminutes in DMS
#   note: vernal equinox is the intersection of the celestial equator and the eliptic 
#         where the eliptic rise above the celestial equator going further east

# right ascension to decimal degree
def hms2degree(hours, minutes, seconds):
    return 15*(hours + minutes/60 + seconds/3600)
# declination to decimal degree
def declination2degree(degrees, arcminutes, arcseconds):
    angle = abs(degrees) + arcminutes/60 + arcseconds/3600
    return angle if degrees > 0 else -angle

# Angular distance - the projected angle between objects as seen from earth
# To crossmatch 2 catalogues we need to compare the angular distance between objects on the celestial sphere
# Here haversine function for angular distance is used, as it is good for avoiding floating point errors.

def angular_distance(ra1, d1, ra2, d2):
    # map to radians, as most trigonometric functions in np and python takes radians rather than degrees
    ra1, d1, ra2, d2 = map(np.radians, [ra1, d1, ra2, d2])
    a = np.sin(np.abs(d1-d2)/2)**2 # ** is power of
    b = np.cos(d1)*np.cos(d2)*np.sin(np.abs(ra1-ra2)/2)**2
    d = 2*np.arcsin(np.sqrt(a+b))
    return np.degrees(d)

# print(angular_distance(21.07, 0.1, 21.15, 8.2)) # 8.100392318146506

# bright radio source: columns
# 1 - objectID (sometimes with asterisk); 2-4 right ascension in HMS; 5-7 declination in DMS; 8 - other data
# return id, ra, dec
def import_bss(file):
    data = np.loadtxt(file, usecols=range(1,7))
    result = [] # id, ra, dec
    for i, row in enumerate(data, 1):
        ascension = hms2degree(row[0], row[1], row[2])
        declination = declination2degree(row[3], row[4], row[5])
        result.append((i, ascension, declination))
    return result

# superCosmos catalog
# return id, ra, dec
def import_super(file):
    data = np.loadtxt(file, delimiter=',', skiprows=1, usecols=[0,1])
    result = [] # id, ra, dec
    for i, row in enumerate(data, 1):
        result.append((i, row[0], row[1])) # RA, Dec in the file
    return result

# brute force Nearest Neighbour where object to match is defined with right ascension and declination
def find_closest(catalog, ascension, declination):
    closest = (None, np.inf) # objectId, distance
    for item in catalog:
        distance = angular_distance(ascension, declination, item[1], item[2])
        if distance < closest[1] : closest = (item[0], distance)
    return closest

# brute force cross matching objects between 2 catalogs, max_distance in decimal degrees
def crossmatch(catalog1, catalog2, max_distance):
    matches = [] # id, id, distance
    no_matches = [] # ids
    for (i, ra1, dec1) in catalog1:
        (j, dist) = find_closest(catalog2, ra1, dec1)
        if dist > max_distance : no_matches.append(i)
        else: matches.append((i, j, dist))
    return matches, no_matches

# uses k-d tree
def crossmatch_v2(catalog1, catalog2, max_distance):
    matches = [] # id, id, distance
    no_matches = [] # ids
    catalogA = [[row[1], row[2]] for row in catalog1] # SkyCoord does not accept id
    catalogB = [[row[1], row[2]] for row in catalog2]
    # catalogA = SkyCoord(ra=catalog_1_ra*u.degree, dec=catalog_1_d*u.degree)
    # catalogB = SkyCoord(ra=catalog_2_ra*u.degree, dec=catalog_2_d*u.degree)
    catalogA = SkyCoord(catalogA*u.degree, frame='icrs')
    catalogB = SkyCoord(catalogB*u.degree, frame='icrs')
    closest_ids, closest_distances, _ = catalogA.match_to_catalog_sky(catalogB)
    for i, (j, dist) in enumerate(zip(closest_ids, closest_distances.value)):
        if dist < max_distance : matches.append((i, j, dist))
        else : no_matches.append(i)
    return matches, no_matches

# TEST SCENARIOS

bss_cat = import_bss('data/bss.dat')
super_cat = import_super('data/super.csv')
max_distance = 40/3600  # 40 arcsecond
match, no_match = crossmatch(bss_cat, super_cat, max_distance)
# match, no_match = crossmatch_v2(bss_cat, super_cat, max_distance)
print(match[:3])
print(no_match[:3])
print(len(no_match))

# EXPECTED OUTPUT
# [(1, 2, 0.00010988610938710059), (2, 4, 0.0007649845967242495), (3, 5, 0.00020863352870707666)]
# [5, 6, 11]
# 9

# max_distance = 5/3600  # 5 arcsecond
# match, no_match = crossmatch(bss_cat, super_cat, max_distance)
# match, no_match = crossmatch_v2(bss_cat, super_cat, max_distance)
# print(match[:3])
# print(no_match[:3])
# print(len(no_match))

# EXPECTED OUTPUT
# [(1, 2, 0.00010988610938710059), (2, 4, 0.0007649845967242495), (3, 5, 0.00020863352870707666)]
# [5, 6, 11]
# 40

#%%

# Week 4

import psycopg2
import numpy as np

def column_stats(table_name, column_name):
  conn = psycopg2.connect(dbname='db', user='grok')
  cursor = conn.cursor()
  cursor.execute('SELECT ' + column_name + ' FROM ' + table_name + ';')
  records = cursor.fetchall()
  array = np.array(records)
  mean = array.mean()
  median = np.median(array)
  return mean, median 

import numpy as np

def query(csvfile):
  data = np.loadtxt(csvfile, delimiter=',')
  result = data[(data[:,2] > 1.0)] # filter by 3rd column > 1.0
  result = result[:,[0,2]]         # return 1st and 3rd columns
  sorted_idx = np.argsort(result[:,1]) # sort by 3rd (now 2nd) column asc
  result = result[sorted_idx]
  return result

def query_v2(f_name):
  data = np.loadtxt(f_name, delimiter=',', usecols=(0, 2))
  return data[data[:, 1]>1, :]

## 
#SELECT p.radius/s.radius AS radius_ratio
#FROM Planet AS p
#INNER JOIN star AS s USING (kepler_id)
#WHERE s.radius > 1.0
#ORDER BY p.radius/s.radius ASC;

import numpy as np

def query(fname_1, fname_2):
  stars = np.loadtxt(fname_1, delimiter=',', usecols=(0, 2))
  planets = np.loadtxt(fname_2, delimiter=',', usecols=(0, 5))

  f_stars = stars[stars[:,1]>1, :]                
  s_stars = f_stars[np.argsort(f_stars[:, 1]), :] 
 
  final = np.zeros((1, 1))
  for i in range(s_stars.shape[0]):
    kep_id = s_stars[i, 0]
    s_radius = s_stars[i, 1]

    matching_planets = planets[np.where(planets[:, 0] == kep_id), 1].T
    final = np.concatenate((final, matching_planets/s_radius))

  return np.sort(final[1:], axis = 0)
query('data/stars.csv', 'data/planets.csv')

#%% WEEK 5

# We will be using flux magnitudes from the Sloan Digital Sky Survey (SDSS) catalogue to create colour indices.
# Flux magnitudes are the total flux (or light) received in five frequency bands (u, g, r, i and z).
# The astronomical colour (or colour index) is the difference between the magnitudes of two filters, i.e. u - g or i - z.
# This index is one way to characterise the colours of galaxies. 
# For example, if the u-g index is high then the object is brighter in ultra violet frequencies than it is in visible green frequencies.
# Colour indices act as an approximation for the spectrum of the object and are useful for classifying stars into different types.
# see python_week5_1.png
# To calculate the redshift of a distant galaxy, the most accurate method is to observe the optical emission lines and measure the shift in wavelength.
# However, this process can be time consuming and is thus infeasible for large samples.
# For many galaxies we simply don't have spectroscopic observations.
# Instead, we can calculate the redshift by measuring the flux using a number of different filters and comparing this to models of what we expect galaxies to look like at different redshifts.
# see python_week5_2.png

# data in the .npy files is npy array with columns: u g r i z ... redshift
# input is the 4 color indices and targets are corresponding redshifts
# Your function should return a tuple of:
# features: a NumPy array of dimensions m ⨉ 4, where m is the number of galaxies;
# targets: a 1D NumPy array of length m, containing the redshift for each galaxy.

# The data argument will be the structured array described on the previous slide. 
# The u flux magnitudes and redshifts can be accessed as a column with data['u'] and data['redshift'].
# The four features are the colours u - g, g - r, r - i and i - z. 
# To calculate the first column of features, subtract the u and g columns, like this:
# import numpy as np
# data = np.load('sdss_galaxy_colors.npy')
# print(data['u'] - data['g'])
# hint:
# features = np.zeros((data.shape[0], 4))
# features[:, 0] = data['u'] - data['g']
import numpy as np
from sklearn.tree import DecisionTreeRegressor

def get_features_targets(data):
  features = np.zeros(shape=(len(data), 4)) # empty mxn array
  features[:, 0] = data['u'] - data['g']
  features[:, 1] = data['g'] - data['r']
  features[:, 2] = data['r'] - data['i']
  features[:, 3] = data['i'] - data['z']
  targets = data['redshift']
  return features, targets

def median_diff(predicted, actual):
  return np.median(np.abs(predicted - actual))

# write a function that splits the data into training and testing subsets
# trains the model and returns the prediction accuracy with median_diff
def validate_model(model, features, targets):
  # split the data into training and testing features and predictions
  split = features.shape[0]//2
  train_features, test_features = features[:split], features[split:]
  train_targets, test_targets = targets[:split], targets[split:]
  # train the model
  model.fit(train_features, train_targets)
  # get the predicted_redshifts
  predictions = model.predict(test_features)
  # use median_diff function to calculate the accuracy
  return median_diff(test_targets, predictions)

data = np.load('data/sdss_galaxy_colors.npy')
features, targets = get_features_targets(data)

# initialize model
dtr = DecisionTreeRegressor()

# validate the model and print the med_diff
diff = validate_model(dtr, features, targets)
print('Median difference: {:f}'.format(diff))

## Week 5 - 2
def accuracy_by_treedepth(features, targets, depths):
  # split the data into testing and training sets
  split = features.shape[0]//2
  train_features, test_features = features[:split], features[split:]
  train_targets, test_targets = targets[:split], targets[split:]
  # initialise arrays or lists to store the accuracies for the below loop
  train_diffs = []
  test_diffs = []
  # loop through depths
  for depth in depths:
    # initialize model with the maximum depth. 
    dtr = DecisionTreeRegressor(max_depth=depth)

    # train the model using the training set
    dtr.fit(train_features, train_targets)

    # get the predictions for the training set and calculate their median_diff
    predictions = dtr.predict(train_features)
    train_diffs.append(median_diff(train_targets, predictions))
    # get the predictions for the testing set and calculate their median_diff
    predictions = dtr.predict(test_features)
    test_diffs.append(median_diff(test_targets, predictions))
  # return the accuracies for the training and testing sets
  return train_diffs, test_diffs  

tree_depths = [i for i in range(1, 36, 2)]
train_med_diffs, test_med_diffs = accuracy_by_treedepth(features, targets, tree_depths)
# Plot the results
train_plot = plt.plot(tree_depths, train_med_diffs, label='Training set')
test_plot = plt.plot(tree_depths, test_med_diffs, label='Validation set')
plt.xlabel("Maximum Tree Depth")
plt.ylabel("Median of Differences")
plt.legend()
plt.show()

## Week 5 - 3
def cross_validate_model(model, features, targets, k):
  kf = KFold(n_splits=k, shuffle=True)

  # initialise a list to collect median_diffs for each iteration of the loop below
  diffs = []
  for train_indices, test_indices in kf.split(features):
    train_features, test_features = features[train_indices], features[test_indices]
    train_targets, test_targets = targets[train_indices], targets[test_indices]
    
    # fit the model for the current set
    model.fit(train_features, train_targets)
    # predict using the model
    predictions = model.predict(test_features)
    # calculate the median_diff from predicted values and append to results array
    diffs.append(median_diff(predictions, test_targets))
 
  # return the list with your median difference values
  return diffs

dtr = DecisionTreeRegressor(max_depth=19)
diffs = cross_validate_model(dtr, features, targets, 10)

# Alternative splitting
# kf = KFold(n_splits=k, shuffle=True)
# for train_indices, test_indices in kf.split(features):

#%% WEEK 5 - 3
import numpy as np
from sklearn.model_selection import KFold
from sklearn.tree import DecisionTreeRegressor

def get_features_targets(data):
  features = np.zeros(shape=(len(data), 4)) # empty mxn array
  features[:, 0] = data['u'] - data['g']
  features[:, 1] = data['g'] - data['r']
  features[:, 2] = data['r'] - data['i']
  features[:, 3] = data['i'] - data['z']
  targets = data['redshift']
  return features, targets

def median_diff(predicted, actual):
  return np.median(np.abs(predicted - actual))


# complete this function
def cross_validate_model(model, features, targets, k):
  kf = KFold(n_splits=k, shuffle=True)

  # declare an array for predicted redshifts from each iteration
  all_predictions = np.zeros_like(targets)

  for train_indices, test_indices in kf.split(features):
    # split the data into training and testing
    train_features, test_features = features[train_indices], features[test_indices]
    train_targets, test_targets = targets[train_indices], targets[test_indices]
    
    # fit the model for the current set
    model.fit(train_features, train_targets) 
    # predict using the model
    predictions = model.predict(test_features)    
    # put the predicted values in the all_predictions array defined above
    all_predictions[test_indices] = predictions

  # return the predictions
  return all_predictions    


# complete this function
def split_galaxies_qsos(data):
  # split the data into galaxies and qsos arrays
  galaxies = data[data['spec_class'] == b'GALAXY']
  qsos = data[data['spec_class'] == b'QSO']
  # return the seperated galaxies and qsos arrays
  return galaxies, qsos


def cross_validate_median_diff(data):
  features, targets = get_features_targets(data)
  dtr = DecisionTreeRegressor(max_depth=19)
  return np.mean(cross_validate_model(dtr, features, targets, 10))

if __name__ == "__main__":
    data = np.load('data/sdss_galaxy_colors.npy')

    # Split the data set into galaxies and QSOs
    galaxies, qsos = split_galaxies_qsos(data)

    # Here we cross validate the model and get the cross-validated median difference
    # The cross_validated_med_diff function is in "written_functions"
    galaxy_med_diff = cross_validate_median_diff(galaxies)
    qso_med_diff = cross_validate_median_diff(qsos)

    # Print the results
    print("Median difference for Galaxies: {:.3f}".format(galaxy_med_diff))
    print("Median difference for QSOs: {:.3f}".format(qso_med_diff))

#%% Week 6

# The features that we will be using to do our galaxy classification are colour index, adaptive moments, eccentricities and concentrations.
# These features are provided as part of the SDSS catalogue.
# We briefly describe these below. Further information how they are calculated can be found here: http://skyserver.sdss.org/dr7/en/help/docs/algorithm.asp

# **Colour indices** are the same colours (u-g, g-r, r-i, and i-z) we used for regression. 
# Studies of galaxy evolution tell us that spiral galaxies have younger star populations and therefore are 'bluer' (brighter at lower wavelengths). 
# Elliptical galaxies have an older star population and are brighter at higher wavelengths ('redder').

# **Eccentricity** approximates the shape of the galaxy by fitting an ellipse to its profile. 
# Eccentricity is the ratio of the two axis (semi-major and semi-minor). 
# The De Vaucouleurs model was used to attain these two axis. To simplify our experiments, we will use the median eccentricity across the 5 filters.

# **Adaptive moments** also describe the shape of a galaxy. They are used in image analysis to detect similar objects at different sizes and orientations. 
# We use the fourth moment here for each band.

# **Concentration** is similar to the luminosity profile of the galaxy, which measures what proportion of a galaxy's total light is emitted within what radius.
# A simplified way to represent this is to take the ratio of the radii containing 50% and 90% of the Petrosian flux.
# Here the Pertossian approach is used. conc = petro50/petro90

# import numpy as np
# data = np.load('week6data/galaxy_catalogue.npy')
# for name, value in zip(data.dtype.names, data[0]):
#  print('{:10} {:.6}'.format(name, value))

# colours: u-g, g-r, r-i, and i-z;
# eccentricity: ecc
# 4th adaptive moments: m4_u, m4_g, m4_r, m4_i, and m4_z;
# 50% Petrosian: petroR50_u, petroR50_r, petroR50_z;

# 90% Petrosian: petroR90_u, petroR90_r, petroR90_z.
# data[0]['u-g'] : to access the u-g colour filter for the first galaxy
# dtr = DecisionTreeClassifier()

# accuracy
import numpy as np
from matplotlib import pyplot as plt
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import cross_val_predict
from sklearn.tree import DecisionTreeClassifier
from support_functions import plot_confusion_matrix, generate_features_targets


# Implement the following function
def calculate_accuracy(predicted, actual):
  return sum(predicted == actual)/len(actual)

data = np.load('data/galaxy_catalogue.npy')
# split the data
features, targets = generate_features_targets(data)
# train the model to get predicted and actual classes
dtc = DecisionTreeClassifier()
predicted = cross_val_predict(dtc, features, targets, cv=10)
# calculate the model score using your function
model_score = calculate_accuracy(predicted, targets)
print("Our accuracy score:", model_score)
# calculate the models confusion matrix using sklearns confusion_matrix function
class_labels = list(set(targets))
model_cm = confusion_matrix(y_true=targets, y_pred=predicted, labels=class_labels)
# Plot the confusion matrix using the provided functions.
plt.figure()
plot_confusion_matrix(model_cm, classes=class_labels, normalize=False)
plt.show()

#%% Week 6 - 2

import numpy as np
from matplotlib import pyplot as plt
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import cross_val_predict
from sklearn.ensemble import RandomForestClassifier
# from support_functions import generate_features_targets, plot_confusion_matrix, calculate_accuracy


# complete this function to get predictions from a random forest classifier
def rf_predict_actual(data, n_estimators):
  # generate the features and targets
  features, targets = generate_features_targets(data)
  # instantiate a random forest classifier using n estimators
  rfc = RandomForestClassifier(n_estimators=n_estimators)
  # get predictions using 10-fold cross validation with cross_val_predict
  predicted = cross_val_predict(rfc, features, targets, cv=10)
  # return the predictions and their actual classes
  return predicted, targets

import numpy as np
import itertools
from matplotlib import pyplot as plt


def calculate_accuracy(predicted_classes, actual_classes, ):
    return sum(actual_classes[:] == predicted_classes[:]) / len(actual_classes)


def generate_features_targets(data):
    output_targets = np.empty(shape=(len(data)), dtype='<U20')
    output_targets[:] = data['class']

    input_features = np.empty(shape=(len(data), 13))
    input_features[:, 0] = data['u-g']
    input_features[:, 1] = data['g-r']
    input_features[:, 2] = data['r-i']
    input_features[:, 3] = data['i-z']
    input_features[:, 4] = data['ecc']
    input_features[:, 5] = data['m4_u']
    input_features[:, 6] = data['m4_g']
    input_features[:, 7] = data['m4_r']
    input_features[:, 8] = data['m4_i']
    input_features[:, 9] = data['m4_z']
    input_features[:, 10] = data['petroR50_u'] / data['petroR90_u']
    input_features[:, 11] = data['petroR50_r'] / data['petroR90_r']
    input_features[:, 12] = data['petroR50_z'] / data['petroR90_z']

    return input_features, output_targets

def plot_confusion_matrix(cm, classes,
                          normalize=False,
                          title='Confusion matrix',
                          cmap=plt.cm.Blues):
    """
    This function prints and plots the confusion matrix.
    Normalization can be applied by setting `normalize=True`.
    """
    plt.imshow(cm, interpolation='nearest', cmap=cmap)
    plt.title(title)
    plt.colorbar()
    tick_marks = np.arange(len(classes))
    plt.xticks(tick_marks, classes, rotation=45)
    plt.yticks(tick_marks, classes)

    if normalize:
        cm = cm.astype('float') / cm.sum(axis=1)[:, np.newaxis]
        print("Normalized confusion matrix")
    else:
        print('Confusion matrix, without normalization')

    print(cm)

    thresh = cm.max() / 2.
    for i, j in itertools.product(range(cm.shape[0]), range(cm.shape[1])):
        plt.text(j, i, "{}".format(cm[i, j]),
                 horizontalalignment="center",
                 color="white" if cm[i, j] > thresh else "black")

    plt.tight_layout()
    plt.ylabel('True Class')
    plt.xlabel('Predicted Class')


data = np.load('data/galaxy_catalogue.npy')

# get the predicted and actual classes
number_estimators = 50              # Number of trees
predicted, actual = rf_predict_actual(data, number_estimators)

# calculate the model score using your function
accuracy = calculate_accuracy(predicted, actual)
print("Accuracy score:", accuracy)

# calculate the models confusion matrix using sklearns confusion_matrix function
class_labels = list(set(actual))
model_cm = confusion_matrix(y_true=actual, y_pred=predicted, labels=class_labels)

# plot the confusion matrix using the provided functions.
plt.figure()
plot_confusion_matrix(model_cm, classes=class_labels, normalize=False)
plt.show()


# %%

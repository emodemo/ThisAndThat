# DATA MANIPULATION
# ImageDataGenerator which allows to quickly set up Python generators
# that can automatically turn image files on disk into batches of pre-processed tensors.
from keras.preprocessing.image import ImageDataGenerator
from keras.preprocessing import image

# 1) DATA AUGMENTATION.. these are just few of the options
datagen = ImageDataGenerator(rotation_range=40, width_shift_range=0.2, height_shift_range=0.2,
      shear_range=0.2, zoom_range=0.2, horizontal_flip=True, fill_mode='nearest')

img_paths = [] # array of the images
img_path = img_paths[3]
# Read the image and resize it
img = image.load_img(img_path, target_size=(150, 150))
# Convert it to a Numpy array with shape (150, 150, 3)
x = image.img_to_array(img)
# Reshape it to (1, 150, 150, 3)
x = x.reshape((1,) + x.shape)
# The .flow() command below generates batches of randomly transformed images.
iterator = datagen.flow(x, batch_size=1)


# 2) PRE-TRAINED NET AND FEATURE EXTRACTION (the convolution part only)
# ALWAYS FREEZE THE PRE-TRAINED PART (trainable=False). As the Dense layer are random at start,
# large weights updates will be propagated through the model, destroying the pre-trained part
# convolution layers are more likely to be generic
# densely connected layers are specific to the set of training classes
# densely connected layers have no spatial information about the objects

# Xception, Inception V3, VGG16, ...
from keras.applications import VGG16
conv_base = VGG16(weights='imagenet', # using pre-trained imagenet
                  include_top=False,  # with without the densely connected part
                  input_shape=(150, 150, 3))  # optional
inputs_batch = [] # prepared data
train_labels = []
train_features = conv_base.predict(inputs_batch)
# create the dense part of the model
from keras import models
from keras import layers
from keras import optimizers
model = models.Sequential()
model.add(layers.Dense(256, activation='relu', input_dim=4 * 4 * 512))
model.add(layers.Dropout(0.5))
model.add(layers.Dense(1, activation='sigmoid'))
model.compile(optimizer=optimizers.RMSprop(lr=2e-5), loss='binary_crossentropy',metrics=['acc'])
# use the conv_base predicted data for input in the model training session
history = model.fit(train_features, train_labels, epochs=30, batch_size=20)
# alternatively add the base to the mode
model = models.Sequential()
conv_base.trainable = False # freeze the pre-trained part
model.add(conv_base)
model.add(layers.Dense(256, activation='relu', input_dim=4 * 4 * 512))
# ... and so on
# 3) FINE TUNING PRE-TRAINED CONVOLUTION NET
# partial unfreeze see the model summary, iterate over the conv_base.layers and unfreeze wanted part
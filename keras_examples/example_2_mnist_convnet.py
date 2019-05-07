from keras import models
from keras import layers
from keras import optimizers
from keras import losses
from keras import metrics
from keras.utils import to_categorical
from keras.datasets import mnist

#(train_images, train_labels), (test_images, test_labels) = mnist.load_data()

model = models.Sequential()
# PART 1/2 - Training the convolution part - feature extraction
# input ere is image_height, image_width, image_channels (28, 28, 1)
# conv2D(output_depth (win_height, win_width)), where output_depth is N of channels
model.add(layers.Conv2D(32, (3, 3), activation='relu', input_shape=(28, 28, 1)))
model.add(layers.MaxPooling2D((2, 2))) # or AvergaePooling, or strides in the convolution layer
model.add(layers.Conv2D(64, (3, 3), activation='relu'))
model.add(layers.MaxPooling2D((2, 2)))
model.add(layers.Conv2D(64, (3, 3), activation='relu'))
# PART 2/2 - Densely connected classifiers
# as the output is 3d, but the result is 1d => flatten
model.add(layers.Flatten())
# model.add(layers.Dropout(0.5)) # dropout example
model.add(layers.Dense(64, activation='relu'))
model.add(layers.Dense(10, activation='softmax'))

model.summary()

model.compile(optimizer=optimizers.RMSprop(lr=0.001),
              loss=losses.categorical_crossentropy,
              metrics=[metrics.binary_accuracy])

# train_images = train_images.reshape((60000, 28, 28, 1))
# train_images = train_images.astype('float32') / 255
# test_images = test_images.reshape((10000, 28, 28, 1))
# test_images = test_images.astype('float32') / 255
# train_labels = to_categorical(train_labels)
# test_labels = to_categorical(test_labels)
#
# history = model.fit(train_images, train_labels, epochs=5, batch_size=64)
#
# test_loss, test_acc = model.evaluate(test_images, test_labels)
# print('test_acc:', test_acc, "test_loss:", test_loss)
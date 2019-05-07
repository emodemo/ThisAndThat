from keras import models
from keras import layers
from keras import optimizers
from keras import losses
from keras import metrics
from keras import activations
from keras import regularizers
from keras.utils import to_categorical
from keras.datasets import mnist

(train_images, train_labels), (test_images, test_labels) = mnist.load_data()

# tensor is defined by 3 key attributes
# RANK (number of axes): Matrix is 2D, Vector is 1D, Scalar is 0D
print(train_images.ndim)     # 3
# SHAPE: N of dimensions across each axis: e.g. (6000, 28, 28) for a 3D tensor
print(train_images.shape)    # (60000, 28, 28)
# DATA TYPE: float32, unit8, float64 ...
print(train_images.dtype)    # unit8

# DEFINE THE MODEL
# hyper-parameters = number and size of layers
# parameters = weights , referred also as model's capacity
model = models.Sequential()
# this layer can be interpreted as a function that takes as input 2D tensor and returns a another 2D tensor
# the function is "output = relu(dot(W, input) + b)", where W and b are tensors that
# are trainable attributes of the layer (kernel and bias respectively)
# 512 is N of hidden units in the layer
# having 512 layers means the W will have (input_dim, 512) shape,
# and the dot() will project the input data onto a 512-Dimensional representation space
# Dimensionality = freedom when learning internal representations
model.add(layers.Dense(512, activation='relu', input_shape=(28 * 28,)))
# same layer but with weight regularization
# model.add(layers.Dense(512, kernel_regularizer=regularizers.l2, activation='relu', input_shape=(28 * 28,)))
# another layer, with softmax this time
model.add(layers.Dense(10, activation=activations.softmax)) # softmax for a single label classification



# DEFINE THE MODEL WITH FUNCTIONAL API
#input_tensor = layers.Input(shape=(28 *28,))
#x = layers.Dense(512, activation='relu')(input_tensor)
#output_tensor = layers.Dense(10, activation='softmax')(x)
#model2 = models.Model(inputs=input_tensor, outputs=output_tensor)

# gradient-descent processes must be based on a single scalar loss value
# => in multi-loss networks all losses are combined
# cross-entropy measures the distances between probability distributions (from Information Theory)
model.compile(optimizer=optimizers.RMSprop(lr=0.001),# adjusts weights to minimize loss function, using back-propagation
              loss=losses.categorical_crossentropy,  # how far the expected output is from the actual
              metrics=[metrics.binary_accuracy])

model.summary()

# TRAIN  without validation or with validation
train_images = train_images.reshape((60000, 28 * 28))
train_images = train_images.astype('float32') / 255
test_images = test_images.reshape((10000, 28 * 28))
test_images = test_images.astype('float32') / 255
# encode labels vie categories and use losses.categorical_crossentropy
train_labels = to_categorical(train_labels)
test_labels = to_categorical(test_labels)
# or encode labels as integers and use losses.sparse_categorical_crossentropy
#train_labels = to_one_hot(train_labels)
#test_labels = to_one_hot(test_labels)

#set apart 10000 for validation
v_images = train_images[:10000]
train_images2 = train_images[10000:]
v_labels = train_labels[:10000]
train_labels2 = train_labels[10000:]

# history = model.fit(train_images, train_labels, epochs=5, batch_size=128) # without validation
history = model.fit(train_images2, train_labels2, epochs=5, batch_size=128, validation_data=(v_images, v_labels))

# SAVE the model after training
# model.save('mnist_example1.h5')

test_loss, test_acc = model.evaluate(test_images, test_labels)
print('test_acc:', test_acc, "test_loss:", test_loss)

# PREDICT
#predictions = model.predict(test_images)
#print(predictions)
# pip install Faker
from faker import Faker
from random import random

fake = Faker()
Faker.seed(random())
fakeme = fake.isbn10(separator="")
print(str(fakeme))
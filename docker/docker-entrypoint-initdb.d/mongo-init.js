print('Start #################################################################');

db = db.getSiblingDB('teste-tecnico');

db.createCollection('user');
db.createCollection('address');

print('END #################################################################');
import pymongo

MONGODB_URI = "mongodb://localhost:27017"

CLIENT = pymongo.MongoClient(MONGODB_URI)
DB = CLIENT['academia']
USER_COLLECTION = DB['users']


from peewee import Model, IntegerField, CharField
from database import db

class BaseModel(Model):
    class Meta:
        database = db

class User(BaseModel):
    uid = IntegerField(unique=True, primary_key=True)
    email = CharField(unique=True)
    password = CharField()
    role = CharField()

    class Meta:
        db_table = 'utilizatori'


if __name__ == '__main__':
    db.connect()
    all_entries = BaseModel.select()
    all_users = User.select()

    for entry in all_users:
        print(f"UID: {entry.uid}, Email: {entry.email}, Role: {entry.role}")
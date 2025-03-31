from models import User
from database import db

def check_if_user_exists(searched_username: str, checked_user: User) -> bool:
    if checked_user:
        return True
    else:
        return False

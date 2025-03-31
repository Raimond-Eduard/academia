from fastapi import HTTPException

from entity.Courses import Courses
from database.database import *

class Mapper:
    @staticmethod
    def map_courses_by_code(code : str):
        courses_collection = DB["courses"]

        course_data = courses_collection.find_one({"code": code})

        if "_id" in course_data:
            course_id = course_data["_id"]
            del course_data["_id"]

        course = Courses(**course_data)
        return course

    @staticmethod
    def get_all_courses():
        courses_collection = DB["courses"]
        courses_data = courses_collection.find()

        courses = [Courses(**course) for course in courses_data]

        return courses

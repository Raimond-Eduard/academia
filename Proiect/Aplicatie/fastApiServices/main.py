from http.client import HTTPException
from typing import List

import httpx
from fastapi.encoders import jsonable_encoder

from database.database import DB

from fastapi import FastAPI
from entity.Courses import Courses
from mapper.Mapper import Mapper

app = FastAPI()

EXTERNAL_API = "http://localhost:8080/api/academia/discipline"


@app.get("/api/academia/courses", response_model=List[Courses],
         responses = {
    200:
        {
            "description": "All courses are returned successfully",
        },
    404:
        {
            "description": "Courses not found",
        }
})
async def get_all_courses():
    courses = Mapper.get_all_courses()
    return courses

@app.get("/api/academia/courses/{code}", response_model=Courses,
         responses={
             200: {
                 "description": "Returns a single course material if it\'s code exists",
             },
             404:
                 {
                     "description": "The requested course does not exist",
                 }
         })
async def get_course_by_code(code: str):
    course = Mapper.map_courses_by_code(code)
    return course


@app.put("/api/academia/courses/{code}", response_model=Courses,
         responses={
             404:
                 {
                     "description": "The requested course does not exist",
                 },
             500:
                 {
                     "description": "The update failed before it could be finished",
                 },
             200:
                 {
                     "description": "The material get\'s updated without any issue",
                     "content": {
                         "application/json": {
                             "example": {
{
    "code": "MS1",
    "name": "Matematici Speciale",
    "evaluationProbes": [
        {
            "type": "Exam",
            "weight": 100
        }
    ],
    "courseMaterial": {
        "type": "PDF",
        "filePath": "TBD"
    },
    "labMaterial": {
        "type": "Structured",
        "contentStructure": [
            "Sem1",
            "Sem2",
            "Sem3",
            "Sem4"
        ]
    }
}
                             }
                         }
                     }
                 }

         })
async def update_selected_course(code: str, course: Courses):

    res = Mapper.map_courses_by_code(code)
    if not res:
        raise HTTPException(status_code=404, detail="Course not found")

    updated_course = DB.courses.find_one_and_update(
        {"code": code},
        {"$set": course.model_dump()})
    if updated_course:
        return Courses(**updated_course)
    else:
        raise HTTPException(status_code=500, details="Update Failed for some reason")

@app.patch("/api/academia/courses/{code}", response_model=Courses,
           responses = {
    200:
        {
            "description": "Course updated succesfully",
            "content": {
                "application/json": {
                    "example": {
"evaluationProbes": [
        {"type": "Exam", "weight": 60},
        {"type": "Lab", "weight": 40}
    ],
                    }
                }
            }
        },
    404:
        {
            "description": "The requested course does not exist",
        },
    500:
        {
            "description": "The update failed before it could be finished",
        }
})
async def update_selected_course_by_code(code: str, partial_update: dict):

    update_data = jsonable_encoder(partial_update)

    existing_course = DB.courses.find_one({"code": code})
    if not existing_course:
        raise HTTPException(status_code=404, detail="You can't update a non-existent course material")

    updated_course = DB.courses.find_one_and_update(
        {"code": code},
        {"$set": update_data},
        return_document=True
    )

    if updated_course:
        return Courses(**updated_course)
    else:
        raise HTTPException(status_code=500, detail="Failed to update this course for some reason that is tottaly unknown by everybody that contributed to this line of code, that means me and you the reader")

@app.post("/api/academia/courses", response_model=Courses,
          responses = {
              200:
                  {
                      "description": "The new course material finished creating with succes",
                  },
              404:
                  {
                      "description": "The course material could not be created because it cannot be found in the existing database",
                  },

              500:
                  {
                      "description": "The serever failed to validate data"
                  }

          })
async def create_course_material(course: Courses):

    async with httpx.AsyncClient() as client:
        response = await client.get(EXTERNAL_API)

    if response.status_code != 200:
        raise HTTPException(status_code=500, detail="This god damn server failed to validate datas")

    valid_course_codes = response.json()
    # print(valid_course_codes)
    # if course.code not in valid_course_codes:
    #     print(course.code)
    #     raise HTTPException(status_code=400, detail="The code you provided was not found in the courses database so please check the available courses")

    val = [c['cod'] for c in valid_course_codes]

    if course.code not in val:
        raise HTTPException(status_code=400, detail="The code you provided was not found in the courses database so please check the available courses")

    new_course_material = jsonable_encoder(course)
    DB.courses.insert_one(new_course_material)

    return course

from pydantic import BaseModel, Field
from typing import Optional, List
from bson import ObjectId

class CourseMaterial(BaseModel):
    type: str
    filePath: Optional[str]

class EvaluationProbe(BaseModel):
    type: str
    weight: int

class LaboratoryMaterial(BaseModel):
    type: str
    contentStructure: List[str]

class Courses(BaseModel):
    code: str
    name: str
    evaluationProbes: List[EvaluationProbe]
    courseMaterial : CourseMaterial
    labMaterial : LaboratoryMaterial

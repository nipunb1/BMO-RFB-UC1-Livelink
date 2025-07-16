from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List, Optional
import uvicorn

app = FastAPI(
    title="Borrower UEN Service",
    description="Mock service for BMO RFB Borrower UEN data",
    version="1.0.0"
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class BorrowerUEN(BaseModel):
    uen: str
    borrower_name: str
    fiscal_year_end: str
    monthly_due_days: int
    quarterly_due_days_na: bool
    semi_annual_due_days_na: bool
    annual_due_days: int
    status: str
    created_date: str
    last_updated: str

class BorrowerSearchResponse(BaseModel):
    borrowers: List[BorrowerUEN]
    total_count: int

mock_borrowers = [
    BorrowerUEN(
        uen="10002/78",
        borrower_name="Transamerica Funding Ventures",
        fiscal_year_end="FY31",
        monthly_due_days=30,
        quarterly_due_days_na=True,
        semi_annual_due_days_na=True,
        annual_due_days=365,
        status="Active",
        created_date="2024-01-15",
        last_updated="2024-07-15"
    ),
    BorrowerUEN(
        uen="10002/80",
        borrower_name="BMO Capital Markets Corp",
        fiscal_year_end="FY31",
        monthly_due_days=30,
        quarterly_due_days_na=False,
        semi_annual_due_days_na=True,
        annual_due_days=365,
        status="Active",
        created_date="2024-02-20",
        last_updated="2024-07-10"
    ),
    BorrowerUEN(
        uen="10002/84",
        borrower_name="BMO Transamerica Funding Ventures",
        fiscal_year_end="FY31",
        monthly_due_days=30,
        quarterly_due_days_na=True,
        semi_annual_due_days_na=True,
        annual_due_days=365,
        status="Active",
        created_date="2024-03-10",
        last_updated="2024-07-16"
    ),
    BorrowerUEN(
        uen="10002/85",
        borrower_name="BMO Financial Services Inc",
        fiscal_year_end="FY30",
        monthly_due_days=45,
        quarterly_due_days_na=False,
        semi_annual_due_days_na=False,
        annual_due_days=365,
        status="Active",
        created_date="2024-04-05",
        last_updated="2024-07-12"
    ),
    BorrowerUEN(
        uen="10002/86",
        borrower_name="BMO Investment Banking Corp",
        fiscal_year_end="FY31",
        monthly_due_days=30,
        quarterly_due_days_na=True,
        semi_annual_due_days_na=True,
        annual_due_days=365,
        status="Active",
        created_date="2024-05-15",
        last_updated="2024-07-08"
    ),
    BorrowerUEN(
        uen="10002/87",
        borrower_name="BMO Private Equity Fund",
        fiscal_year_end="FY29",
        monthly_due_days=60,
        quarterly_due_days_na=False,
        semi_annual_due_days_na=True,
        annual_due_days=365,
        status="Active",
        created_date="2024-06-01",
        last_updated="2024-07-05"
    )
]

@app.get("/")
async def root():
    return {"message": "BMO RFB Borrower UEN Service", "version": "1.0.0"}

@app.get("/healthz")
async def healthz():
    return {"status": "ok"}

@app.get("/health")
async def health_check():
    return {"status": "healthy", "service": "borrower-uen-service"}

@app.get("/api/borrowers", response_model=BorrowerSearchResponse)
async def get_borrowers(
    search: Optional[str] = None,
    limit: int = 10,
    offset: int = 0
):
    filtered_borrowers = mock_borrowers
    
    if search:
        search_lower = search.lower()
        filtered_borrowers = [
            borrower for borrower in mock_borrowers
            if (search_lower in borrower.borrower_name.lower() or 
                search_lower in borrower.uen.lower())
        ]
    
    total_count = len(filtered_borrowers)
    paginated_borrowers = filtered_borrowers[offset:offset + limit]
    
    return BorrowerSearchResponse(
        borrowers=paginated_borrowers,
        total_count=total_count
    )

@app.get("/api/borrowers/{uen}", response_model=BorrowerUEN)
async def get_borrower_by_uen(uen: str):
    for borrower in mock_borrowers:
        if borrower.uen == uen:
            return borrower
    
    raise HTTPException(status_code=404, detail=f"Borrower with UEN {uen} not found")

@app.get("/api/borrowers/search/{name}", response_model=BorrowerSearchResponse)
async def search_borrowers_by_name(name: str):
    name_lower = name.lower()
    filtered_borrowers = [
        borrower for borrower in mock_borrowers
        if name_lower in borrower.borrower_name.lower()
    ]
    
    return BorrowerSearchResponse(
        borrowers=filtered_borrowers,
        total_count=len(filtered_borrowers)
    )

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8001)

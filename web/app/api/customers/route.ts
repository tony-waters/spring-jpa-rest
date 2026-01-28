import { NextRequest, NextResponse } from "next/server";

export async function GET(req: NextRequest) {
  const base = "http://spring-api:8080";
  const url = new URL("/api/customers", base);

  // pass through paging params
  const page = req.nextUrl.searchParams.get("page");
  const size = req.nextUrl.searchParams.get("size");
  const sort = req.nextUrl.searchParams.get("sort");
  if (page) url.searchParams.set("page", page);
  if (size) url.searchParams.set("size", size);
  if (sort) url.searchParams.set("sort", sort);

  const res = await fetch(url, {
    // keep it simple; later you can add auth headers/cookies
    headers: { Accept: "application/json" },
    cache: "no-store",
  });

  const body = await res.text(); // keep status/body transparent
  return new NextResponse(body, {
    status: res.status,
    headers: { "content-type": res.headers.get("content-type") ?? "application/json" },
  });
}

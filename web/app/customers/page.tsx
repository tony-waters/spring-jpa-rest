type CustomerWithOrderCount = {
    customerId: number;
    firstName: string;
    lastName: string;
    orderCount: number;
};

type Page<T> = {
    content: T[];
    number: number;
    size: number;
    totalElements: number;
    totalPages: number;
};

export default async function CustomersPage() {
    const res = await fetch("http://localhost:3000/api/customers?page=0&size=10", { cache: "no-store" });
    const page: Page<CustomerWithOrderCount> = await res.json();

    return (
        <main style={{ padding: 24 }}>
            <h1>Customers</h1>
            <ul>
                {page.content.map(c => (
                    <li key={c.customerId}>
                        {c.firstName} {c.lastName} — orders: {c.orderCount}
                    </li>
                ))}
            </ul>
            <p>
                Page {page.number + 1} of {page.totalPages} (total {page.totalElements})
            </p>
        </main>
    );
}

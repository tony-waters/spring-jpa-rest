# spring-jpa-rest
REST layer over the spring-jpa repo

# Overview

# Service Layer

## highlights

### moving validation from the service to the validation api

We want to include the business logic in our Service layer,
we can often end up adding a lot of validation.

Here is what CustomerCommandServiceImpl looked like before refactoring.
I started wondering about the repetition of validation logic.

<code>
    @Override
    public CustomerReadDto updateCustomer(long id, CustomerUpdateDto dto) {
        // Use @NotNull in method parameter instead?
        if (dto == null) {
            throw new IllegalArgumentException("CustomerUpdateDto cannot be null");
        }
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found: id=" + id));
        // Client-driven optimistic locking (fast fail with a clear message)
        // use @NotNull validation instead?
        if (dto.version() == null) {
            throw new IllegalArgumentException("Missing version for update (id=" + id + ")");
        }
        // create @ValidVersion or @OptimisticLocking validation instead?
        if (customer.getVersion().equals(dto.version())) {
            throw new ConflictException(
                    "Stale version for customer id=" + id
                            + " (expected " + customer.getVersion()
                            + ", got " + dto.version() + ")"
            );
        }
        customerMapper.updateEntityFromUpdateDto(dto, customer);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toReadDto(saved);
    }
</code>

if we push the validation to Validators it becomes this:

<code>
    @Override
    public CustomerReadDto updateCustomer(long id, @NotNull CustomerUpdateDto dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found: id=" + id));
        customerMapper.updateEntityFromUpdateDto(dto, customer);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toReadDto(saved);
    }
</code>



## testing

# controllers

## highlights

## testing

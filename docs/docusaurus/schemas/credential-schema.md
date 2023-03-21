# Credential Schema Introduction

## Abstract

This document describes the purpose, supported formats, and technical details of the Credential Schema implementation in
the Atala PRISM Platform.

## 1. Introduction

Credential Schema is a data template for the Verifiable Credentials.
It contains claims (attributes) of the Verifiable Credentials, credential schema author, type, name, version, and proof
of authorship.
By putting schema definitions on a public blockchain, they are available for all verifiers to examine to determine the
semantic interoperability of the Credential.

The PRISM Platform supports the following specifications of the credential schemas:

- [Verifiable Credentials JSON Schema 2022](https://w3c-ccg.github.io/vc-json-schemas/)
- [AnonCreds Schema](https://hyperledger.github.io/anoncreds-spec/#term:schemas) - planned

The signed credential schema allows doing following verifications:

- semantic verification of the verifiable credentials
- authorship verification

The author can use credential schema to issue the following types of verifiable credentials:

- JSON Verifiable Credential
- JSON-LD Verifiable Credential
- all types above but encoded as JWT

Limitations and constraints of the PRISM Platform v2.0:

- The Issuer does not sign the Credential Schema
- The Issuer does not publish the Credential Schema to the VDR (the Cardano blockchain)

## 2. Terminology

### Credential Schema

The Credential Schema is a template that defines a set of attributes the Issuer uses to issue the Verifiable Credential.

### Schema Registry

The registry is where the Credential Schema is published and available for parties.

### Issuer, Holder, Verifier

These are well-known roles in the SSI domain.

## 2. Credential Schema Attributes

### guid (UUID)

It is the globally unique identifier of the credential schema.
It is bound to the `author`, `version`, and `id` fields as it is composed of the bytes of the `longId` string.

### id (UUID)

The locally unique identifier of the schema.

### longId (String)

Resource identifier of the given credential schema composed from the author's DID reference, id, and version fields.
Example: `{author}/{id}?version={version}`

**_NOTE:_**
According to the [W3C specification](https://w3c-ccg.github.io/vc-json-schemas/#id), this field is locally unique and is
a combination of the Issuer `DID`, `uuid`, and `version`.

For example:

`did:example:MDP8AsFhHzhwUvGNuYkX7T/06e126d1-fa44-4882-a243-1e326fbe21db?version=1.0`

`longId`, `id` might be reconsidered in a new versions of the PRISM Platform.

---

### type (String)

It is a type of the supported JSON Schema of the credential schema.
It describes the JSON Schema of the Credential Schema described in this document.
In the current implementation, this field must always be set to the value in the following example:

```json
{
  "type": "https://w3c-ccg.github.io/vc-json-schemas/schema/2.0/schema.json"
}
```

---

### name (String)

It is a human-readable name for the schema.
Example:

```json
{
  "name": [
    "DrivingLicence"
  ]
}
```

---

### description (String)

It is a human-readable description of the schema.

**NOTE:** this field might be removed later as it's not a part of W3C specification but rather the internal field of the
JSON schema.

---

### version (String)

It is a version of the schema that contains the revision of the credential schema in [SemVer](https://semver.org/)
format.
Example:

```json
{
  "version": "1.0.0"
}
```

The version field must be used for the schema evolution and describe the impact of the changes.
For the breaking changes, the `major` version must be increased.
In the current implementation, the PRISM Platform doesn't validate whether the new version is backward compatible.
This logic might be implemented later, so the Issuer is responsible for correctly setting the credential schema's next
version.

---

### author (DID)

DID of the identity which authored the credential schema.
Example:

```json
{
  "author": "did:prism:4a5b5cf0a513e83b598bbea25cd6196746747f361a73ef77068268bc9bd732ff"
}
```

---

### authored (DateTime)

[RFC3339](https://www.rfc-editor.org/rfc/rfc3339) date on which the credential schema was created. A piece of Metadata.
Example:

```json
{
  "authored": "2022-03-10T12:00:00Z"
}
```

---

### schema (JSON Schema)

A valid [JSON-SCHEMA](https://json-schema.org/) where the credential schema semantic is defined.
JSON Schema must be composed according to <https://json-schema.org/draft/2020-12/schema> schema.
Example:

```json
{
  "$id": "driving-license-1.0.0",
  "$schema": "https://json-schema.org/draft/2020-12/schema",
  "description": "Driving License",
  "type": "object",
  "properties": {
    "credentialSubject": {
      "type": "object",
      "properties": {
        "emailAddress": {
          "type": "string",
          "format": "email"
        },
        "givenName": {
          "type": "string"
        },
        "familyName": {
          "type": "string"
        },
        "dateOfIssuance": {
          "type": "datetime"
        },
        "drivingLicenseID": {
          "type": "string"
        },
        "drivingClass": {
          "type": "integer"
        },
        "required": [
          "emailAddress",
          "familyName",
          "dateOfIssuance",
          "drivingLicenseID",
          "drivingClass"
        ],
        "additionalProperties": true
      }
    }
  }
}
```

---

### tags (String[])

It is a set of tokens that allow one to look up and filter the credential schema records.
This field is not a part of W3C specification and is used by the PRISM Platform for filtering the records.
Example:

```json
{
  "tags": [
    "id",
    "driving"
  ]
}
```

### proof (object)

The proof field is a JOSE object containing the credential schema's signature.
The following fields are used:

- type
- created
- verificationMethod
- proofPurpose
- proofValue
- domain
- jws

Example:

```json
{
  "proof": {
    "type": "Ed25519Signature2018",
    "created": "2022-03-10T12:00:00Z",
    "verificationMethod": "did:prism:4a5b5cf0a513e83b598bbea25cd6196746747f361a73ef77068268bc9bd732ff#key-1",
    "proofPurpose": "assertionMethod",
    "proofValue": "FiPfjknHikKmZ...",
    "jws": "eyJhbGciOiJFZERTQSIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il0sImt0eSI6Ik...",
    "domain": "prims.atala.com"
  }
}
```

---

## References

- [Verifiable Credentials JSON Schema 2022](https://w3c-ccg.github.io/vc-json-schemas/)
- [Verifiable Credential Data Integrity 1.0](https://www.w3.org/TR/vc-data-integrity/)
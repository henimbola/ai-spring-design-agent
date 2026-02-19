# AI Designer

## Product Vision
AI Designer is an agentic design assistant that helps teams transform a raw communication request into a professional visual document.

The goal is simple: reduce the time spent staring at a blank page and accelerate the path from idea to approved design.

## What Problem It Solves
Design teams often receive incomplete or rough briefs, then lose time on:
- finding a starting visual direction,
- trying multiple design options under deadline pressure,
- handling repeated feedback cycles.

AI Designer is built to support this early creative phase with structure and speed.

## What The Product Is
AI Designer is not just a one-shot prompt.
It is a guided design agent workflow that:
1. Understands the raw demand and constraints.
2. Proposes multiple creative style directions.
3. Produces an improved layout while preserving required content.

Current focus:
- **Tri-fold dépliant (A4 landscape, recto-verso)**.

Next focus:
- **Flyers** and other communication formats.

## Product Principles
- Keep the original intent and structure of the brief.
- Preserve exact wording when required.
- Offer creativity through clear style options.
- Add validation checks to reduce hallucinations.
- Keep a human approval step before final output.

## Architecture Schema

```text
User Input
(raw brief + reference image + wording constraints)
    |
    v
ReceiverController
(GET /receiver/chat)
    |
    v
DesignGenerationService
(orchestration + monitoring logs)
    |
    +--> 1) SpecExtractionService
    |         |
    |         v
    |     Spec Validation
    |     (6 panels + missing input checks)
    |         |
    |         +--> invalid -> Validation Failure Response (request stops)
    |         |
    |         +--> valid
    |                |
    +---------------> v
              2) StyleProposalService
              (generate 5 creative styles)
                       |
                       v
                 Style Validation
                 (count + consistency)
                       |
                       +--> invalid -> Validation Failure Response (request stops)
                       |
                       +--> valid
                              |
                              v
                        Select style
                              |
                              v
                    3) HtmlRenderService
                       (final HTML/CSS)
                              |
                              v
                      Render Validation
                  (exact wording + panels)
                              |
                +-------------+-------------+
                |                           |
                v                           v
      Success Response              Validation Failure Response
      (validated HTML/CSS)          (request stops, no auto-loop)
```

## Current Outcome
The project already demonstrates a first end-to-end flow:
- from draft/reference input,
- to style reasoning,
- to generated final layout output.

## Target Outcome
AI Designer should become a reliable "design copilot" for communication teams, producing high-quality first versions faster and making review cycles more efficient.

Long term, the ambition is to make outputs usable in real production tools (including Canva-oriented workflows).
